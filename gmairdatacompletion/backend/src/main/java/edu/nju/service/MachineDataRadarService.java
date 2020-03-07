package edu.nju.service;

import edu.nju.model.MachineAvgDailyData;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 10:18
 * @description: 此接口中所有方法中的duringTime参数为期望天数减一，
 *               比如期望得到近30天的数据，则duringTime为29.
 *               其中的bestMethod和duringTime建议调用Common模块的Constant类中的常量.
 */
public interface MachineDataRadarService {

    int getAvgIndoorPm25Daily(String uid, int bestMethod, int duringTime);

    int getAvgInnerPm25Daily(String uid, int bestMethod, int duringTime);

    int getAvgCo2Daily(String uid, int bestMethod, int duringTime);

    int getAvgHumidDaily(String uid, int bestMethod, int duringTime);

    int getAvgTempDaily(String uid, int bestMethod, int duringTime);

    int getAvgVolumeDaily(String uid, int bestMethod, int duringTime);

    int getAvgMachineOpenTimeDaily(String uid, int bestMethod, int duringTime);

    List<MachineAvgDailyData> getAvgDailyData(int bestMethod, int duringTime);
}
