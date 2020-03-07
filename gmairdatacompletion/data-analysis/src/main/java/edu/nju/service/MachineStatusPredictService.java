package edu.nju.service;

import edu.nju.bo.MachineStatisticData;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 14:59
 * @description: TODO
 */
public interface MachineStatusPredictService {

    MachineStatisticData gradientPredict(MachineStatisticData lastTwo, MachineStatisticData lastOne);

    MachineStatisticData usePreviousPredict(MachineStatisticData last);
}
