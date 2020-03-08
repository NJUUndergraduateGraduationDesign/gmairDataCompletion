package edu.nju.service.status.impl;

import com.google.common.collect.Lists;
import edn.nju.util.MyMath;
import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.IndoorPm25DailyDao;
import edu.nju.model.monthly.DefeatUserPercent;
import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.model.status.IndoorPm25Daily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.IndoorPm25DailyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Override
    public List<String> getAllUids(int methodCode, long startTime, long endTime) {
        return indoorPm25DailyDao.getAllUids(methodCode, startTime, endTime);
    }

    @Override
    public double getAverage(String uid, int methodCode, long startTime, long endTime) {
        return indoorPm25DailyDao.getAverage(uid, methodCode, startTime, endTime);
    }

    @Override
    public DefeatUserPercent getDefeatUserPercent(String uid, int methodCode, long startTime, long endTime) {
        double averagePm25 = getAverage(uid, methodCode, startTime, endTime);
        List<String> uidList = getAllUids(methodCode, startTime, endTime);
        List<Double> pm25List = Lists.newArrayList();
        for (String str : uidList) {
            double pm25 = getAverage(str, methodCode, startTime, endTime);
            pm25List.add(pm25);
        }
        double defeatUserPercent = 0;
        int smallThan = (int) pm25List.stream().filter(e -> e < averagePm25).count();
        int allBesideMe = pm25List.size() - 1;
        if (allBesideMe == 0) {
            defeatUserPercent = 100;
        } else {
            defeatUserPercent = (1 - (double) smallThan / allBesideMe) * 100;
        }
        return new DefeatUserPercent(averagePm25, defeatUserPercent);
    }


}
