package edu.nju.dao.impl;

import edu.nju.dao.AvgDailyDataDao;
import edu.nju.model.machine.MachineAvgDailyData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/9 19:00
 * @description: TODO
 */

@Repository
public class AvgDailyDataDaoImpl extends BaseDaoImpl<MachineAvgDailyData> implements AvgDailyDataDao {
    @Override
    public void insertBatch(List<MachineAvgDailyData> data) {
        for (MachineAvgDailyData one : data) {
            add(one);
        }
    }
}
