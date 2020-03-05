package edu.nju;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import edn.nju.enums.MachineStatusTypeEnum;
import edn.nju.util.HttpDeal;
import edu.nju.controller.MachineController;
import edu.nju.controller.UserLocationController;
import edu.nju.controller.UserStatisticController;
import edu.nju.model.Location;
import edu.nju.model.MachineV3Status;
import edu.nju.model.machine.MachineLatestStatus;
import edu.nju.model.statistic.UserLocation;
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
    @Resource
    UserLocationController userLocationController;
    @Resource
    UserStatisticController userStatisticController;

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
     * 执行成功的话应该不会打印任何错误信息，如果有错误信息则有数据没有存进数据库，需要重新运行
     */
    @Test
    void insertCityAndProvinceInfo() {
        String url = DISTRICT + "?key=" + KEY;
        JSONObject response = JSON.parseObject(HttpDeal.getResponse(url));
        if (!StringUtils.isEmpty(response) && response.getInteger("status") == 0) {
            JSONArray data = response.getJSONArray("result");
            JSONArray provinces = data.getJSONArray(0);
            JSONArray cities = data.getJSONArray(1);
            JSONArray districts = data.getJSONArray(2);
            JSONObject province, city, district;
            //省
            for (int i = 0; i < provinces.size(); i++) {
                province = provinces.getJSONObject(i);
                String provinceName = province.getString("fullname");
                int start = province.getJSONArray("cidx").toJavaList(Integer.class).get(0);
                int end = province.getJSONArray("cidx").toJavaList(Integer.class).get(1);
                //市
                for (int j = start; j <= end; j++) {
                    city = cities.getJSONObject(j);
                    String cityId = city.getString("id");
                    String cityName = city.getString("fullname");

                    Location location = new Location(cityId, cityName, provinceName);
                    if (!locationService.addOneLocation(location)){
                        System.out.println("ERROR AT: " + cityId + ": " + provinceName + " " + cityName);
                    }

                    if (city.getJSONArray("cidx") != null) {
                        int s = city.getJSONArray("cidx").toJavaList(Integer.class).get(0);
                        int e = city.getJSONArray("cidx").toJavaList(Integer.class).get(1);
                        //区
                        for (int k = s; k <= e; k++) {
                            district = districts.getJSONObject(k);
                            String districtId = district.getString("id");
                            Location l = new Location(districtId, cityName, provinceName);
                            if (!locationService.addOneLocation(l)){
                                System.out.println("ERROR AT: " + districtId + ": " + provinceName + " " + cityName);
                            }
                        }
                    }
                }
            }
        }
        Location last1 = new Location("undefined","未知","未知");
        Location last2 = new Location("null","未知","未知");
        if(locationService.addOneLocation(last1) && locationService.addOneLocation(last2)) {
            System.out.println("Add the 'undefined' and 'null' successfully!");
        }
    }

    /**
     * 用于对一个uid进行原始数据补全
     */
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

    /**
     * 用于对一个uid进行数据统计分析
     */
    @Test
    void testAnalyze() {
        machineStatusHandleServiceImpl.handleV2Data(Lists.newArrayList("F0FE6BAA617C"));
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
    void testMachineController() {
        MachineQueryCond queryCond = new MachineQueryCond();
        queryCond.setCurPage(1);
        queryCond.setPageSize(10);
        queryCond.setCreateTimeGTE(new Date(118, Calendar.SEPTEMBER, 6));
        queryCond.setCreateTimeLTE(new Date());
        queryCond.setIsPower(1);
        queryCond.setUid("F0FE6BAA761E");
        Map<String, List> machineBasicInfos =
                (Map<String, List>) machineController.getList(queryCond).getData();
        System.out.println(machineBasicInfos.get("machineList").size() + " " +
                machineBasicInfos.get("machineList").get(0));
    }

    @Test
    void testUserLocationController() {
        List<UserLocation> res1 = (List<UserLocation>) userLocationController.nationalUserList().getData();
        List<UserLocation> res2 =
                (List<UserLocation>) userLocationController.provincialUserList("江苏省").getData();
        int count = 0;
        for (UserLocation one : res1) {
            System.out.println(one.getName() + ": " + one.getValue());
            count += one.getValue();
        }
        System.out.println("total users: " + count);
    }

    @Test
    void testUserStatisticsController() {
        Map<String, Integer> res =
                (Map<String, Integer>) userStatisticController.getUserDataRadar("F0FE6BAA617C").getData();
        for (String key : res.keySet()) {
            System.out.println(key + ": " + res.get(key));
        }
    }
}
