package edu.nju;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import edn.nju.enums.MachineStatusTypeEnum;
import edn.nju.util.HttpDeal;
import edu.nju.controller.MachineController;
import edu.nju.model.Location;
import edu.nju.model.MachineV3Status;
import edu.nju.model.machine.MachineLatestStatus;
import edu.nju.request.MachineQueryCond;
import edu.nju.model.MachineV2Status;
import edu.nju.model.User;
import edu.nju.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GmairDataCompletionApplication.class)
class GmairDataCompletionApplicationTests {

    private static final String DISTRICT = "http://apis.map.qq.com/ws/district/v1/list";
    private static final String KEY = "LYEBZ-H6TCW-TOXRD-OSOKF-UBG25-53BFW";

    @Resource
    MachineV2StatusService machineV2StatusServiceImpl;
    @Resource
    MachineV3StatusService machineV3StatusServiceImpl;
    @Resource
    MachinePartialStatusService machinePartialStatusServiceImpl;
    @Resource
    DataCompletion dataCompletion;
    @Resource
    UserService userService;
    @Resource
    MachineStatusHandleService machineStatusHandleServiceImpl;
    @Resource
    MachineLatestStatusService machineLatestStatusService;
    @Resource
    MachineController machineController;
    @Resource
    LocationService locationService;

    /**
     * 用于添加User表的dataType字段
     */
    @Test
    void changeTableUser() {
        List<String> allV2Uids = machineV2StatusServiceImpl.getAllUids();
        int count = 0;
        for (String oneId : allV2Uids) {
            User user = userService.findByUid(oneId);
            if (user == null) {
                count++;
                System.out.println("V2: " + oneId);
                continue;
            }
            user.setDataType(MachineStatusTypeEnum.MACHINE_V2_STATUS.getCode());
            userService.update(user);
        }

        List<String> allV3Uids = machineV3StatusServiceImpl.getAllUids();
        for (String oneId : allV3Uids) {
            User user = userService.findByUid(oneId);
            if (user == null) {
                count++;
                System.out.println("V3: " + oneId);
                continue;
            }
            user.setDataType(MachineStatusTypeEnum.MACHINE_V3_STATUS.getCode());
            userService.update(user);
        }
        System.out.println("total: " + count);
    }

    /**
     * 用于建立MachineLatestStatus表
     */
    @Test
    void insertLatestStatus() {
        List<User> res = userService.findAllValidUsers();
        for (User one : res) {
            if (one.getDataType() == MachineStatusTypeEnum.MACHINE_V2_STATUS.getCode()) {
                MachineV2Status status = machineV2StatusServiceImpl.getLatestRecord(one.getUid());
                MachineLatestStatus latestStatus = new MachineLatestStatus(one.getUid(),
                        status.getPower(),
                        status.getHeat(),
                        status.getMode());
                if (!machineLatestStatusService.add(latestStatus))
                    System.out.println("oops");
            }
            else if (one.getDataType() == MachineStatusTypeEnum.MACHINE_V3_STATUS.getCode()) {
                MachineV3Status status = machineV3StatusServiceImpl.getLatestRecord(one.getUid());
                MachineLatestStatus latestStatus = new MachineLatestStatus(one.getUid(),
                        status.getStatus(),
                        status.getHeat(),
                        status.getMode());
                if (!machineLatestStatusService.add(latestStatus)) {
                    System.out.println("oops");
                }
            }
        }
    }

    /**
     * 用于向数据库存储行政区域的编码和名称
     */
    @Test
    void insertCityAndProvinceInfo() {
        String url = DISTRICT + "?key=" + KEY;
        JSONObject response = JSON.parseObject(HttpDeal.getResponse(url));
        if (!StringUtils.isEmpty(response) && response.getInteger("status") == 0) {
            JSONArray data = response.getJSONArray("result");
            JSONArray provinces = data.getJSONArray(0);
            JSONArray cities = data.getJSONArray(1);
            JSONObject province, city;
            for (int i = 0; i < provinces.size(); i++) {
                province = provinces.getJSONObject(i);
                String provinceName = province.getString("fullname");
                int start = province.getJSONArray("cidx").toJavaList(Integer.class).get(0);
                int end = province.getJSONArray("cidx").toJavaList(Integer.class).get(1);
                for (int j = start; j <= end; j++) {
                    city = cities.getJSONObject(j);
                    String cityId = city.getString("id");
                    String cityName = city.getString("fullname");
                    Location location = new Location(cityId, cityName, provinceName);
                    if (!locationService.addOneLocation(location)){
                        System.out.println("ERROR AT: " + cityId + ": " + provinceName + " " + cityName);
                    }
                }
            }
        }
        Location last = new Location("undefined","未知","未知");
        if(locationService.addOneLocation(last)) {
            System.out.println("Add the 'undefined' successfully!");
        }
    }

    @Test
    void testLogs() {
        log.info("testLog");
    }

    @Test
    void testFindById() {
        MachineV2Status machineV2Status = machineV2StatusServiceImpl.findById("5d9249a980a5f93e1e393d48");
        System.out.println(machineV2Status);
    }


    @Test
    void testFetchBatch1() {
        System.out.println("startTime:" + new Date());
        Page<MachineV2Status> machineV2StatusPage = machineV2StatusServiceImpl.fetchBatchByUid("F0FE6BC350A0", 0, 50);
        System.out.println("endTime:" + new Date());
        System.out.println(machineV2StatusPage.getSize());
        System.out.println(machineV2StatusPage.getContent());
    }

    @Test
    void testFetchBatch2() {
        System.out.println("startTime:" + new Date());
        List<MachineV2Status> machineV2StatusList = machineV2StatusServiceImpl.fetchBatchByUid("F0FE6BC350A0", 1569780000000L, 1569945600000L);
        System.out.println("endTime:" + new Date());
        System.out.println(machineV2StatusList.get(0));
    }

    @Test
    void testFetchBatch3() {
        String uid = "F0FE6BAA617C";
        long startTime = machineV2StatusServiceImpl.getStartTimeByUid(uid);
        long endTime = machineV2StatusServiceImpl.getLatestTimeByUid(uid);
        long timeInterval = 5 * 60 * 1000;
        long timeBias = 10 * 1000;
        List<MachineV2Status> machineV2StatusList = machineV2StatusServiceImpl.fetchBatchByUid(
                uid, startTime, endTime, timeInterval, timeBias);
        System.out.println(machineV2StatusList.size());
        System.out.println(machineV2StatusList);
    }

    @Test
    void testGetStartTimeByUid() {
        long startTime = machineV2StatusServiceImpl.getStartTimeByUid("F0FE6BC350A0");
        System.out.println("startTime:" + startTime);
    }

    @Test
    void testGetAllUids() {
        List<String> v2UidList = machineV2StatusServiceImpl.getAllUids();
        List<String> v3UidList = machineV3StatusServiceImpl.getAllUids();
        List<String> partialUidList = machinePartialStatusServiceImpl.getAllUids();
        System.out.println("v2UidList:" + v2UidList.size());
        System.out.println("v3UidList:" + v3UidList.size());
        System.out.println("partialUidList:" + partialUidList.size());
        Set<String> v2v3UidSet = Sets.intersection(Sets.newHashSet(v2UidList), Sets.newHashSet(v3UidList));
        Set<String> v2partialUidSet = Sets.intersection(Sets.newHashSet(v2UidList), Sets.newHashSet(partialUidList));
        Set<String> v3partialUidSet = Sets.intersection(Sets.newHashSet(v3UidList), Sets.newHashSet(partialUidList));
        Set<String> v2v3partialUidSet = Sets.intersection(v2v3UidSet, Sets.newHashSet(partialUidList));
        System.out.println("v2v3:" + v2v3UidSet.size());
        System.out.println("v2partial:" + v2partialUidSet.size());
        System.out.println("v3partial:" + v3partialUidSet.size());
        System.out.println("v2v3partial:" + v2v3partialUidSet.size());
    }

    @Test
    void testInsert() {
        Page<MachineV2Status> machineV2StatusPage = machineV2StatusServiceImpl.fetchBatchByUid("F0FE6BC350A0", 0, 1);
        MachineV2Status machineV2Status = machineV2StatusPage.getContent().get(0);
        Date time = new Date(machineV2Status.getCreateAt() + 100000);
        machineV2Status.setCreateAt(time.getTime());
        log.info("toCreate:" + machineV2Status);
        machineV2StatusServiceImpl.insertBatch(Lists.newArrayList(machineV2Status));
    }

    @Test
    void testDataCompletion() {
        System.out.println("start time: " + new Date());
        dataCompletion.v2Completion(Lists.newArrayList("F0FE6BAA617C"));
        System.out.println("end time: " + new Date());
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAnalyze() {
        machineStatusHandleServiceImpl.handleV2Data(Lists.newArrayList("F0FE6BAA617C"));
    }

    @Test
    void testMethods() {
    }

    @Test
    void testMachineController() {
        MachineQueryCond queryCond = new MachineQueryCond();
        queryCond.setCurPage(1);
        queryCond.setPageSize(10);
        queryCond.setCreateTimeGTE(new Date(118, Calendar.SEPTEMBER, 6));
        queryCond.setCreateTimeLTE(new Date());
        queryCond.setIsPower(1);
        Map<String, List> machineBasicInfos =
                (Map<String, List>) machineController.getList(queryCond).getData();
        System.out.println(machineBasicInfos.get("machineList").size() + " " +
                machineBasicInfos.get("machineList").get(0));
    }
}
