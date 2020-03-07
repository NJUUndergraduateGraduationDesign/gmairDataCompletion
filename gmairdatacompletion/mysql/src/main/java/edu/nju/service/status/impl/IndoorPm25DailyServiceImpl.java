package edu.nju.service.status.impl;

import edn.nju.util.MyMath;
import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.IndoorPm25DailyDao;
import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.model.status.IndoorPm25Daily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.IndoorPm25DailyService;
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
public class IndoorPm25DailyServiceImpl extends BaseDailyHourlyServiceImpl<IndoorPm25Daily> implements IndoorPm25DailyService {
    @Resource
    private IndoorPm25DailyDao indoorPm25DailyDao;
    @Resource
    public void setDao(BaseDailyHourlyDao<IndoorPm25Daily> indoorPm25DailyDao) {
        super.setDao(indoorPm25DailyDao);
    }

    @Override
    public int getAverageData(String uid, int methodCode, long startTime, long endTime) {
        List<Double> store = indoorPm25DailyDao.getAverageList(uid, methodCode, startTime, endTime);
        return store == null ? 0 : MyMath.getAvgWithWeight(store);
    }

    @Override
    public List<AvgDataDaily> getAverageList(String uid, int methodCode, long startTime, long endTime) {
        return indoorPm25DailyDao.getAvgListWithDate(uid, methodCode, startTime, endTime);
    }

    @Override
    public List<Double> getAvgList(String uid, int methodCode, long startTime, long endTime) {
        return indoorPm25DailyDao.getAverageList(uid, methodCode, startTime, endTime);
    }
}
