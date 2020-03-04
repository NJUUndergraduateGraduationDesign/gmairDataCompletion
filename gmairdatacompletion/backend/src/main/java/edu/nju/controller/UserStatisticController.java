package edu.nju.controller;

import edn.nju.ResponseDTO;
import edn.nju.util.TimeUtil;
import edu.nju.service.status.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Bright Chan
 * @date: 2020/3/4 12:19
 * @description: TODO
 */

@RestController
@RequestMapping("/statistic/user")
public class UserStatisticController {

    private static final int lastMonth = 30;
    private static final int bestMethod = 1;
    private static final String[] colName = {"average_pm25", "average_pm25", "average_co2",
                                             "average_humid", "average_temp", "average_volume"};

    @Resource
    IndoorPm25DailyService indoorPm25DailyService;
    @Resource
    InnerPm25DailyService innerPm25DailyService;
    @Resource
    Co2DailyService co2DailyService;
    @Resource
    HumidDailyService humidDailyService;
    @Resource
    TempDailyService tempDailyService;
    @Resource
    VolumeDailyService volumeDailyService;

    @GetMapping("/radar")
    public ResponseDTO getUserDataRadar(@RequestParam String uid) {
        long end = indoorPm25DailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, lastMonth);
        int avgIndoorPm25 = indoorPm25DailyService.getAverageData(uid, colName[0], bestMethod, start, end);

        end = innerPm25DailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, lastMonth);
        int avgInnerPm25 = innerPm25DailyService.getAverageData(uid, colName[1], bestMethod, start, end);

        end = co2DailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, lastMonth);
        int avgCo2 = co2DailyService.getAverageData(uid, colName[2], bestMethod, start, end);

        end = humidDailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, lastMonth);
        int avgHumid = humidDailyService.getAverageData(uid, colName[3], bestMethod, start, end);

        end = tempDailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, lastMonth);
        int avgTemp = tempDailyService.getAverageData(uid, colName[4], bestMethod, start, end);

        end = volumeDailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, lastMonth);
        int avgVolume = volumeDailyService.getAverageData(uid, colName[5], bestMethod, start, end);

        Map<String, Integer> res = new HashMap<>();
        res.put("indoorPm25", avgIndoorPm25);
        res.put("outdoorPm25", avgInnerPm25);
        res.put("co2", avgCo2);
        res.put("humid", avgHumid);
        res.put("temp", avgTemp);
        res.put("volume", avgVolume);

        return ResponseDTO.ofSuccess(res);
    }

}
