package edu.nju.service.impl;

import edn.nju.constant.Constant;
import edn.nju.util.TimeUtil;
import edu.nju.bo.MachineStatisticData;
import edu.nju.service.DataPredictService;
import edu.nju.service.MachineDataPredictService;
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
    //使用最近30天的数据为观测值
    private static final int TIME_INTERVAL_DAYS = 30;

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
    private DataPredictService dataPredictService;

    @Override
    public MachineStatisticData predict(String uid) {
        long end = indoorPm25DailyService.getLatestTime(uid);
        long start = TimeUtil.getNDayBefore(end, TIME_INTERVAL_DAYS);
        List<Double> indoorPm25Data = indoorPm25DailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        double indoorPm25Predict = dataPredictService.holtLinearTrendPredict(indoorPm25Data);
        indoorPm25Predict=Math.max(0,indoorPm25Predict);

        end = innerPm25DailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, TIME_INTERVAL_DAYS);
        List<Double> innerPm25Data = innerPm25DailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        double innerPm25Predict = dataPredictService.holtLinearTrendPredict(innerPm25Data);
        innerPm25Predict=Math.max(0,innerPm25Predict);

        end = co2DailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, TIME_INTERVAL_DAYS);
        List<Double> co2Data = co2DailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        double co2Predict = dataPredictService.holtLinearTrendPredict(co2Data);
        co2Predict=Math.max(0,co2Predict);

        end = humidDailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, TIME_INTERVAL_DAYS);
        List<Double> humidData = humidDailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        double humidPredict = dataPredictService.holtLinearTrendPredict(humidData);
        humidPredict=Math.max(0,humidPredict);

        end = tempDailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, TIME_INTERVAL_DAYS);
        List<Double> tempData = tempDailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        double tempPredict = dataPredictService.holtLinearTrendPredict(tempData);

        end = volumeDailyService.getLatestTime(uid);
        start = TimeUtil.getNDayBefore(end, TIME_INTERVAL_DAYS);
        List<Double> volumeData = volumeDailyService.getAvgList(uid,
                Constant.MachineData.BEST_METHOD, start, end);
        double volumePredict = dataPredictService.holtLinearTrendPredict(volumeData);
        volumePredict=Math.max(0,volumePredict);

        return new MachineStatisticData(indoorPm25Predict, innerPm25Predict, co2Predict, humidPredict, tempPredict, volumePredict);
    }
}
