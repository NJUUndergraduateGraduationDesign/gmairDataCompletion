package edu.nju.controller;

import edn.nju.ResponseDTO;
import edn.nju.constant.Constant;
import edn.nju.util.TimeUtil;
import edu.nju.bo.MachineStatisticData;
import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.service.MachineDataPredictService;
import edu.nju.service.MachineDataRadarService;
import edu.nju.service.status.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: Bright Chan
 * @date: 2020/3/4 12:19
 * @description: TODO
 */

@Slf4j
@RestController
@RequestMapping("/statistic/user")
public class UserStatisticController {

    private static final String firstDayInYear = "-01-01 00:00:00";

    @Resource
    private IndoorPm25DailyService indoorPm25DailyService;
    @Resource
    private PowerDailyService powerDailyService;
    @Resource
    private MachineDataRadarService machineDataRadarService;
    @Resource
    private MachineDataPredictService machineDataPredictService;

    @GetMapping("/radar")
    public ResponseDTO getUserDataRadar(@RequestParam String uid) {
        int avgIndoorPm25 = machineDataRadarService.getAvgIndoorPm25Daily(uid,
                Constant.MachineData.BEST_METHOD, Constant.MachineData.LAST_MONTH);
        int avgInnerPm25 = machineDataRadarService.getAvgInnerPm25Daily(uid,
                Constant.MachineData.BEST_METHOD, Constant.MachineData.LAST_MONTH);
        int avgCo2 = machineDataRadarService.getAvgCo2Daily(uid,
                Constant.MachineData.BEST_METHOD, Constant.MachineData.LAST_MONTH);
        int avgHumid = machineDataRadarService.getAvgHumidDaily(uid,
                Constant.MachineData.BEST_METHOD, Constant.MachineData.LAST_MONTH);
        int avgTemp = machineDataRadarService.getAvgTempDaily(uid,
                Constant.MachineData.BEST_METHOD, Constant.MachineData.LAST_MONTH);
        int avgVolume = machineDataRadarService.getAvgVolumeDaily(uid,
                Constant.MachineData.BEST_METHOD, Constant.MachineData.LAST_MONTH);

        Map<String, Integer> res = new HashMap<>();
        res.put("indoorPm25", avgIndoorPm25);
        res.put("outdoorPm25", avgInnerPm25);
        res.put("co2", avgCo2);
        res.put("humid", avgHumid);
        res.put("temp", avgTemp);
        res.put("volume", avgVolume);

        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/openTime")
    public ResponseDTO getAvgMachineOpenTime(@RequestParam String uid) {
        int avgTime = machineDataRadarService.getAvgMachineOpenTimeDaily(uid,
                Constant.MachineData.BEST_METHOD, Constant.MachineData.LAST_MONTH);
        Map<String, Integer> res = new HashMap<>();
        res.put("time", avgTime);
        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/calendar/pm25")
    public ResponseDTO getAvgIndoorPm25PerDayThisYear(@RequestParam String uid) {
        long end = indoorPm25DailyService.getLatestTime(uid);
        long start = getFirstDayOfThisYear(end);
        List<AvgDataDaily> store = indoorPm25DailyService.getAverageList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        Map<String, Integer> res = new HashMap<>();
        for (AvgDataDaily one : store) {
            Date oneTime = new Date(one.getDate());
            res.put(TimeUtil.dateToStrDay(oneTime), (int) Math.round(one.getAvgData()));
        }
        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/calendar/openTime")
    public ResponseDTO getAvgMachineOpenTimePerDayThisYear(@RequestParam String uid) {
        long end = powerDailyService.getLatestTime(uid);
        long start = getFirstDayOfThisYear(end);
        List<AvgDataDaily> store = powerDailyService.getAverageList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        Map<String, Integer> res = new HashMap<>();
        for (AvgDataDaily one : store) {
            Date oneTime = new Date(one.getDate());
            String oneTimeStr = TimeUtil.dateToStrDay(oneTime);
            res.put(oneTimeStr, (int) Math.round(one.getAvgData()));
        }
        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/forecastData")
    public ResponseDTO getForecastData(@RequestParam String uid) {
        Map<String, Integer> res = new HashMap<>();
        MachineStatisticData predictData = machineDataPredictService.gradientPredict(uid);
        res.put("indoorPm25", (int) Math.round(predictData.getIndoorPm25()));
        res.put("outdoorPm25", (int) Math.round(predictData.getInnerPm25()));
        res.put("co2", (int) Math.round(predictData.getCo2()));
        res.put("humid", (int) Math.round(predictData.getHumid()));
        res.put("temp", (int) Math.round(predictData.getTemp()));
        res.put("volume", (int) Math.round(predictData.getVolume()));
        return ResponseDTO.ofSuccess(res);
    }

    private long getFirstDayOfThisYear(long end) {
        long start = 0;
        Date endTime = new Date(end);
        int thisYear = endTime.getYear() + 1900;    //正确的年份需要加上1900
        try {
            Date startTime = TimeUtil.strToDate(thisYear + firstDayInYear);
            start = startTime.getTime();
        } catch (Exception e) {
            log.error("failed to get the start time!");
        }
        return start;
    }

}
