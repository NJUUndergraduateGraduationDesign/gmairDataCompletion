package edu.nju;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import edn.nju.constant.Constant;
import edn.nju.enums.MachineStatusTypeEnum;
import edn.nju.util.HttpDeal;
import edn.nju.util.TimeUtil;
import edu.nju.controller.MachineController;
import edu.nju.controller.UserLocationController;
import edu.nju.controller.UserStatisticController;
import edu.nju.dto.MonthlyReportDTO;
import edu.nju.method.KNN;
import edu.nju.method.Mean;
import edu.nju.method.UsePrevious;
import edu.nju.model.*;
import edu.nju.model.machine.MachineBasicInfo;
import edu.nju.model.machine.MachineLatestStatus;
import edu.nju.model.statistic.UserLocation;
import edu.nju.request.MachineQueryCond;
import edu.nju.service.*;
import edu.nju.service.status.InnerPm25DailyService;
import edu.nju.shiro.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
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
    @Resource
    InnerPm25DailyService innerPm25DailyService;
    @Resource
    Mean mean;
    @Resource
    UsePrevious usePrevious;
    @Resource
    KNN knn;
    @Resource
    UserReportService userReportService;

    @Test
    @Transactional
    @Rollback(false)
    void insertPassword() {
        List<User> userList = userService.findAllUsers();
        for (User user : userList) {
            String password= ShiroUtil.encrypt(user.getUid(),user.getCodeValue());
            user.setPassword(password);
        }
    }

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
            int overCount = 0;
            try {
                long end = innerPm25DailyService.getLatestTime(one.getUid());
                long start = TimeUtil.getNDayBefore(end, Constant.MachineData.LAST_MONTH);
                overCount = innerPm25DailyService.getOverCount
                        (one.getUid(), Constant.MachineData.BEST_METHOD, start, end);
            } catch (Exception e) {

            }
            if (one.getDataType() == MachineStatusTypeEnum.MACHINE_V2_STATUS.getCode()) {
                MachineV2Status status = machineV2StatusServiceImpl.getLatestRecord(one.getUid());
                MachineLatestStatus latestStatus = new MachineLatestStatus(one.getUid(),
                        status.getPower(),
                        status.getHeat(),
                        status.getMode(),
                        overCount);
                if (!machineLatestStatusService.add(latestStatus))
                    System.out.println("oops");
            } else if (one.getDataType() == MachineStatusTypeEnum.MACHINE_V3_STATUS.getCode()) {
                MachineV3Status status = machineV3StatusServiceImpl.getLatestRecord(one.getUid());
                MachineLatestStatus latestStatus = new MachineLatestStatus(one.getUid(),
                        status.getStatus(),
                        status.getHeat(),
                        status.getMode(),
                        overCount);
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
                    if (!locationService.addOneLocation(location)) {
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
                            if (!locationService.addOneLocation(l)) {
                                System.out.println("ERROR AT: " + districtId + ": " + provinceName + " " + cityName);
                            }
                        }
                    }
                }
            }
        }
        Location last1 = new Location("undefined", "未知", "未知");
        Location last2 = new Location("null", "未知", "未知");
        if (locationService.addOneLocation(last1) && locationService.addOneLocation(last2)) {
            System.out.println("Add the 'undefined' and 'null' successfully!");
        }
    }

    /**
     * 用于对一个uid进行原始数据补全
     */
    @Test
    void testDataCompletion() {
        dataCompletion.partialCompletion(Lists.newArrayList("F0FE6BAA617C"));
        dataCompletion.v2Completion(Lists.newArrayList("F0FE6BAA617C"));
        dataCompletion.v3Completion(Lists.newArrayList("98D8639C3543"));
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
        machineStatusHandleServiceImpl.handlePartialData(Lists.newArrayList("F0FE6BAA617C"));
        machineStatusHandleServiceImpl.handleV2Data(Lists.newArrayList("F0FE6BAA617C"));
        machineStatusHandleServiceImpl.handleV3Data(Lists.newArrayList("98D8639C3543"));
    }

    @Test
    void testMachineController() {
        MachineQueryCond queryCond = new MachineQueryCond();
        queryCond.setCurPage(1);
        queryCond.setPageSize(10);
        //2018-09-06
        queryCond.setCreateTimeGTE(new Date(118, Calendar.SEPTEMBER, 6));
        queryCond.setCreateTimeLTE(new Date());
        queryCond.setIsPower(1);
        queryCond.setOverCountGTE(0);
        queryCond.setOverCountLTE(2);
        Map<String, List<MachineBasicInfo>> machineBasicInfos =
                (Map<String, List<MachineBasicInfo>>) machineController.getList(queryCond).getData();
        List<MachineBasicInfo> res = machineBasicInfos.get("machineList");
        Assert.assertNotNull(res);
        Assert.assertEquals(10, res.size());
        Assert.assertEquals("MachineBasicInfo(uid=F0FE6BAA611B, codeValue=42A188A207410, city=闵行区," +
                " isPower=1, mode=1, bindTime=2018-09-13 11:58:25.0, overCount=0, heat=0)", res.get(0).toString());
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
        /*
        Map<String, Integer> res1 =
                (Map<String, Integer>) userStatisticController.getUserDataRadar("F0FE6BAA617C").getData();
        for (String key : res1.keySet()) {
            System.out.println(key + ": " + res1.get(key));
        }
        System.out.println("==========");

        Map<String, Integer> res2 = (Map<String, Integer>)
                userStatisticController.getAvgMachineOpenTime("F0FE6BAA617C").getData();
        for (String key : res2.keySet()) {
            System.out.println(key + ": " + res2.get(key));
        }
        System.out.println("==========");

        Map<String, Integer> res3 = (Map<String, Integer>)
                userStatisticController.getAvgIndoorPm25PerDayThisYear("F0FE6BAA617C").getData();
        for (String key : res3.keySet()) {
            System.out.println(key + ": " + res3.get(key));
        }
        System.out.println("==========");

        Map<String, Integer> res4 = (Map<String, Integer>)
                userStatisticController.getAvgMachineOpenTimePerDayThisYear("F0FE6BAA617C").getData();
        for (String key : res4.keySet()) {
            System.out.println(key + ": " + res4.get(key));
        }
        System.out.println("==========");
         */
    }

    @Test
    void testPredict() {
        /*
        Map<String, Integer> res1 =
                (Map<String, Integer>) userStatisticController.getForecastData("F0FE6BAA617C").getData();
        for (String key : res1.keySet()) {
            System.out.println(key + ": " + res1.get(key));
        }
         */
    }

    @Test
    void testCompletionMethods() {
        MachinePartialStatus one = new MachinePartialStatus("uid", "name", 1,
                false, 0, 0);
        MachinePartialStatus two = new MachinePartialStatus("uid", "name", 2,
                false, (long) Constant.Completion.PARTIAL_INTERVAL, 0);
        MachinePartialStatus three = new MachinePartialStatus("uid", "name", 3,
                false, (long) Constant.Completion.PARTIAL_INTERVAL * 2, 0);
        MachinePartialStatus four = new MachinePartialStatus("uid", "name", 4,
                false, (long) Constant.Completion.PARTIAL_INTERVAL * 4, 0);
        MachinePartialStatus five = new MachinePartialStatus("uid", "name", 5,
                false, (long) Constant.Completion.PARTIAL_INTERVAL * 5, 0);
        MachinePartialStatus six = new MachinePartialStatus("uid", "name", 6,
                false, (long) Constant.Completion.PARTIAL_INTERVAL * 6, 0);
        List<MachinePartialStatus> store = new ArrayList<>();
        store.add(one);
        store.add(two);
        store.add(three);
        store.add(four);
        store.add(five);
        store.add(six);

        List<MachinePartialStatus> resByMean = mean.partialMean(store);
        List<MachinePartialStatus> resByUsePrevious = usePrevious.partialUsePrevious(store);
        List<MachinePartialStatus> resByKNN = knn.partialKNN(store);
        //补全的数据量应该均为1
        Assert.assertNotNull(resByMean);
        Assert.assertNotNull(resByUsePrevious);
        Assert.assertNotNull(resByKNN);
        Assert.assertEquals(1, resByMean.size());
        Assert.assertEquals(1, resByUsePrevious.size());
        Assert.assertEquals(1, resByKNN.size());
        //补全的缺失数据应如下
        Assert.assertEquals("MachinePartialStatus(uid=uid, name=name, data=3, blockFlag=false," +
                " createAt=10800000, completeCode=1)", resByMean.get(0).toString());
        Assert.assertEquals("MachinePartialStatus(uid=uid, name=name, data=3, blockFlag=false," +
                " createAt=10800000, completeCode=2)", resByUsePrevious.get(0).toString());
        Assert.assertEquals("MachinePartialStatus(uid=uid, name=name, data=4, blockFlag=false," +
                " createAt=10800000, completeCode=3)", resByKNN.get(0).toString());
    }

    @Test
    void testUserReport() {
        MonthlyReportDTO res = userReportService.getMonthlyReport("F0FE6BAA617C");
        Assert.assertNotNull(res);
        Assert.assertEquals("MonthlyReportDTO(openDaysCount=6, mostOpenDay=Tue Oct 01 00:00:00 CST 2019," +
                " mostOpenDayHoursCount=23.983333333333334, mostOpenHourGTE=8, mostOpenHourLTE=9," +
                " mostOpenHourMinutesCount=6.551020408163265, mostUseMode=自动, mostUseModeHoursCount=45.2," +
                " pm25Average=27.305289557128184, defeatUserPercent=100.0, categoryEnvironment=2)", res.toString());
    }
}
