package edu.nju.controller;

import edn.nju.ResponseDTO;
import edn.nju.util.TimeUtil;
import edu.nju.model.statistic.AvgDataDaily;
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

    private static final int lastMonth = 29;
    private static final String firstDayInYear = "-01-01 00:00:00";
    private static final int bestMethod = 1;

    @Resource
    private IndoorPm25DailyService indoorPm25DailyService;
    @Resource
    private InnerPm25DailyService innerPm25DailyService;
    @Resource
    private Co2DailyService co2DailyService;
    @Resource
    private HumidDailyService humidDailyService;
    @Resource
    private TempDailyService tempDailyService;
    @Resource
    private VolumeDailyService volumeDailyService;
    @Resource
    private PowerDailyService powerDailyService;

    @GetMapping("/radar")
    public ResponseDTO getUserDataRadar(@RequestParam String uid) {
        long end = indoorPm25DailyService.getLatestTime(uid);
        int avgIndoorPm25 = indoorPm25DailyService.getAverageData(uid, bestMethod,
                TimeUtil.getNDayBefore(end, lastMonth), end);

        end = innerPm25DailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, lastMonth);
        int avgInnerPm25 = innerPm25DailyService.getAverageData(uid, bestMethod,
                start, end);

        end = co2DailyService.getLatestTime(uid);
        int avgCo2 = co2DailyService.getAverageData(uid, bestMethod,
                TimeUtil.getNDayBefore(end, lastMonth), end);

        end = humidDailyService.getLatestTime(uid);
        int avgHumid = humidDailyService.getAverageData(uid, bestMethod,
                TimeUtil.getNDayBefore(end, lastMonth), end);

        end = tempDailyService.getLatestTime(uid);
        int avgTemp = tempDailyService.getAverageData(uid, bestMethod,
                TimeUtil.getNDayBefore(end, lastMonth), end);

        end = volumeDailyService.getLatestTime(uid);
        int avgVolume = volumeDailyService.getAverageData(uid, bestMethod,
                TimeUtil.getNDayBefore(end, lastMonth), end);

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
        long end = powerDailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, lastMonth);
        int avgTime = powerDailyService.getAvgMachineOpenTime(uid, bestMethod, start, end);
        Map<String, Integer> res = new HashMap<>();
        res.put("time", avgTime);
        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/calendar/pm25")
    public ResponseDTO getAvgIndoorPm25PerDayThisYear(@RequestParam String uid) {
        long end = indoorPm25DailyService.getLatestTime(uid);
        long start = getFirstDayOfThisYear(end);
        List<AvgDataDaily> store = indoorPm25DailyService.getAverageList(uid, bestMethod, start, end);
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
        List<AvgDataDaily> store = powerDailyService.getAverageList(uid, bestMethod, start, end);
        Map<String, Integer> res = new HashMap<>();
        for (AvgDataDaily one : store) {
            Date oneTime = new Date(one.getDate());
            String oneTimeStr = TimeUtil.dateToStrDay(oneTime);
            res.put(oneTimeStr, (int) Math.round(one.getAvgData()));
        }
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
