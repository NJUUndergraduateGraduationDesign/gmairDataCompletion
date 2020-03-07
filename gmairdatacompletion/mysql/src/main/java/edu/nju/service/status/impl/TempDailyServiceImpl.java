package edu.nju.service.status.impl;

import edn.nju.util.MyMath;
import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.TempDailyDao;
import edu.nju.model.status.TempDaily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.TempDailyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:22
 * @description：
 */

@Transactional
@Service
public class TempDailyServiceImpl extends BaseDailyHourlyServiceImpl<TempDaily> implements TempDailyService {
    @Resource
    private TempDailyDao tempDailyDao;
    @Resource
    public void setDao(BaseDailyHourlyDao<TempDaily> tempDailyDao) {
        super.setDao(tempDailyDao);
    }

    @Override
    public int getAverageData(String uid, int methodCode, long startTime, long endTime) {
        List<Double> store = tempDailyDao.getAverageList(uid, methodCode, startTime, endTime);
        return store == null ? 0 : MyMath.getAvgWithWeight(store);
    }
}
