package edu.nju.service.impl;

import edn.nju.constant.Constant;
import edn.nju.util.TimeUtil;
import edu.nju.bo.MachineStatisticData;
import edu.nju.service.MachineDataPredictService;
import edu.nju.service.MachineStatusPredictService;
import edu.nju.service.status.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 15:44
 * @description: TODO
 */

@Service
public class MachineDataPredictImpl implements MachineDataPredictService {

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
    private MachineStatusPredictService machineStatusPredictService;

    @Override
    public MachineStatisticData gradientPredict(String uid) {
        MachineStatisticData lastOne = new MachineStatisticData();
        MachineStatisticData lastTwo = new MachineStatisticData();

        long end = indoorPm25DailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, 1);
        List<Double> lastData = indoorPm25DailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        lastOne.setIndoorPm25(lastData.get(lastData.size() - 1));
        lastTwo.setIndoorPm25(lastData.get(lastData.size() - 2));

        end = innerPm25DailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, 1);
        lastData = innerPm25DailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        lastOne.setInnerPm25(lastData.get(lastData.size() - 1));
        lastTwo.setInnerPm25(lastData.get(lastData.size() - 2));

        end = co2DailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, 1);
        lastData = co2DailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        lastOne.setCo2(lastData.get(lastData.size() - 1));
        lastTwo.setCo2(lastData.get(lastData.size() - 2));

        end = humidDailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, 1);
        lastData = humidDailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        lastOne.setHumid(lastData.get(lastData.size() - 1));
        lastTwo.setHumid(lastData.get(lastData.size() - 2));

        end = tempDailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, 1);
        lastData = tempDailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        lastOne.setTemp(lastData.get(lastData.size() - 1));
        lastTwo.setTemp(lastData.get(lastData.size() - 2));

        end = volumeDailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, 1);
        lastData = volumeDailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        lastOne.setVolume(lastData.get(lastData.size() - 1));
        lastTwo.setVolume(lastData.get(lastData.size() - 2));
        return machineStatusPredictService.gradientPredict(lastTwo, lastOne);
    }

    @Override
    public MachineStatisticData usePreviousPredict(String uid) {
        MachineStatisticData last = new MachineStatisticData();

        long lastTime = indoorPm25DailyService.getLatestTime(uid);
        List<Double> lastData = indoorPm25DailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, lastTime, lastTime);
        last.setIndoorPm25(lastData.get(lastData.size() - 1));

        lastTime = innerPm25DailyService.getLatestTime(uid);
        lastData = innerPm25DailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, lastTime, lastTime);
        last.setInnerPm25(lastData.get(lastData.size() - 1));

        lastTime = co2DailyService.getLatestTime(uid);
        lastData = co2DailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, lastTime, lastTime);
        last.setCo2(lastData.get(lastData.size() - 1));

        lastTime = humidDailyService.getLatestTime(uid);
        lastData = humidDailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, lastTime, lastTime);
        last.setHumid(lastData.get(lastData.size() - 1));

        lastTime = tempDailyService.getLatestTime(uid);
        lastData = tempDailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, lastTime, lastTime);
        last.setTemp(lastData.get(lastData.size() - 1));

        lastTime = volumeDailyService.getLatestTime(uid);
        lastData = volumeDailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, lastTime, lastTime);
        last.setVolume(lastData.get(lastData.size() - 1));
        return machineStatusPredictService.usePreviousPredict(last);
    }
}
