package edu.nju.dao;

import edu.nju.model.machine.MachineAvgDailyData;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/9 18:58
 * @description: TODO
 */
public interface AvgDailyDataDao extends BaseDao<MachineAvgDailyData> {

    void insertBatch(List<MachineAvgDailyData> data);
}
