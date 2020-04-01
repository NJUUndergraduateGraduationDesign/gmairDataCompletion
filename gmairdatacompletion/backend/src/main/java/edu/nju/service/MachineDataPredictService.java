package edu.nju.service;

import edu.nju.bo.MachineStatisticData;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 15:41
 * @description: TODO
 */
public interface MachineDataPredictService {
    MachineStatisticData predict(String uid);
}
