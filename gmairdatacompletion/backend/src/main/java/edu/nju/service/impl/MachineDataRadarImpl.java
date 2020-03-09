package edu.nju.service.impl;

import edn.nju.util.TimeUtil;
import edu.nju.model.machine.MachineAvgDailyData;
import edu.nju.model.User;
import edu.nju.service.AvgDailyDataService;
import edu.nju.service.MachineDataRadarService;
import edu.nju.service.UserService;
import edu.nju.service.status.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 10:24
 * @description: TODO
 */

@Service
public class MachineDataRadarImpl implements MachineDataRadarService {

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
    @Resource
    private UserService userService;
    @Resource
    private AvgDailyDataService avgDailyDataService;

    @Override
    public int getAvgIndoorPm25Daily(String uid, int bestMethod, int duringTime) {
        long end = indoorPm25DailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, duringTime);
        return indoorPm25DailyService.getAverageData(uid, bestMethod, start, end);
    }

    @Override
    public int getAvgInnerPm25Daily(String uid, int bestMethod, int duringTime) {
        long end = innerPm25DailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, duringTime);
        return innerPm25DailyService.getAverageData(uid, bestMethod, start, end);
    }

    @Override
    public int getAvgCo2Daily(String uid, int bestMethod, int duringTime) {
        long end = co2DailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, duringTime);
        return co2DailyService.getAverageData(uid, bestMethod, start, end);
    }

    @Override
    public int getAvgHumidDaily(String uid, int bestMethod, int duringTime) {
        long end = humidDailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, duringTime);
        return humidDailyService.getAverageData(uid, bestMethod, start, end);
    }

    @Override
    public int getAvgTempDaily(String uid, int bestMethod, int duringTime) {
        long end = tempDailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, duringTime);
        return tempDailyService.getAverageData(uid, bestMethod, start, end);
    }

    @Override
    public int getAvgVolumeDaily(String uid, int bestMethod, int duringTime) {
        long end = volumeDailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, duringTime);
        return volumeDailyService.getAverageData(uid, bestMethod, start, end);
    }

    @Override
    public int getAvgMachineOpenTimeDaily(String uid, int bestMethod, int duringTime) {
        long end = powerDailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, duringTime);
        return powerDailyService.getAvgMachineOpenTime(uid, bestMethod, start, end);
    }

    @Override
    public void getAvgDailyData(int bestMethod, int duringTime) {
        List<MachineAvgDailyData> res = new ArrayList<>();
        List<String> allUsers = co2DailyService.getUserIds();
        for (String uid : allUsers) {
            MachineAvgDailyData oneRes = new MachineAvgDailyData();
            oneRes.setUid(uid);
            oneRes.setAvgIndoorPm25(getAvgIndoorPm25Daily(uid, bestMethod, duringTime));
            oneRes.setAvgInnerPm25(getAvgInnerPm25Daily(uid, bestMethod, duringTime));
            oneRes.setAvgCo2(getAvgCo2Daily(uid, bestMethod, duringTime));
            res.add(oneRes);
        }
        avgDailyDataService.insertBatch(res);
    }
}
