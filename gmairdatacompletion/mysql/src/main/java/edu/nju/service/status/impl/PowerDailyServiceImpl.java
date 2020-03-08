package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.PowerDailyDao;
import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.model.status.PowerDaily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.PowerDailyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:21
 * @description：
 */

@Transactional
@Service
public class PowerDailyServiceImpl extends BaseDailyHourlyServiceImpl<PowerDaily> implements PowerDailyService {
    @Resource
    private PowerDailyDao powerDailyDao;

    @Resource
    public void setDao(BaseDailyHourlyDao<PowerDaily> powerDailyDao) {
        super.setDao(powerDailyDao);
    }

    @Override
    public int getAvgMachineOpenTime(String uid, int methodCode, long startTime, long endTime) {
        return (int) Math.round(powerDailyDao.getAvgMachineOpenTime(uid, methodCode, startTime, endTime));
    }

    @Override
    public List<AvgDataDaily> getAverageList(String uid, int methodCode, long startTime, long endTime) {
        return powerDailyDao.getAvgListWithDate(uid, methodCode, startTime, endTime);
    }

    @Override
    public int getOpenCount(String uid, int methodCode, long startTime, long endTime) {
        return powerDailyDao.getOpenCount(uid, methodCode, startTime, endTime);
    }

    @Override
    public PowerDaily getMostOpenDay(String uid, int methodCode, long startTime, long endTime) {
        return powerDailyDao.getMostOpenDay(uid, methodCode, startTime, endTime);
    }
}
