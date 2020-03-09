package edu.nju.service.impl;

import edu.nju.dao.AvgDailyDataDao;
import edu.nju.model.machine.MachineAvgDailyData;
import edu.nju.service.AvgDailyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/9 19:04
 * @description: TODO
 */

@Service
@Transactional
public class AvgDailyDataServiceImpl implements AvgDailyDataService {

    @Autowired
    private AvgDailyDataDao avgDailyDataDao;

    @Override
    public void insertBatch(List<MachineAvgDailyData> data) {
        avgDailyDataDao.insertBatch(data);
    }
}
