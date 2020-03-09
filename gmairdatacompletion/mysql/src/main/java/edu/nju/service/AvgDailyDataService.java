package edu.nju.service;

import edu.nju.model.machine.MachineAvgDailyData;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/9 19:03
 * @description: TODO
 */
public interface AvgDailyDataService {

    void insertBatch(List<MachineAvgDailyData> data);

}
