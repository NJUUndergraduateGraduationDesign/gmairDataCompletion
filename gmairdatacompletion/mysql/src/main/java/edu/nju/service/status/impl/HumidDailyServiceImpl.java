package edu.nju.service.status.impl;

import edn.nju.util.MyMath;
import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.HumidDailyDao;
import edu.nju.model.status.HumidDaily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.HumidDailyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:19
 * @description：
 */

@Transactional
@Service
public class HumidDailyServiceImpl extends BaseDailyHourlyServiceImpl<HumidDaily> implements HumidDailyService {
    @Resource
    private HumidDailyDao humidDailyDao;
    @Resource
    public void setDao(BaseDailyHourlyDao<HumidDaily> humidDailyDao) {
        super.setDao(humidDailyDao);
    }

    @Override
    public int getAverageData(String uid, int methodCode, long startTime, long endTime) {
        List<Double> store = humidDailyDao.getAverageList(uid, methodCode, startTime, endTime);
        return MyMath.getAvgWithWeight(store);
    }
}
