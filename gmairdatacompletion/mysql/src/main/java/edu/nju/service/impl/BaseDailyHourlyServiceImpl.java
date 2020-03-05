package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.service.BaseDailyHourlyService;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 1:16
 * @description：
 */

@Transactional
@Service
public abstract class BaseDailyHourlyServiceImpl<T> implements BaseDailyHourlyService<T> {

    protected BaseDailyHourlyDao<T> dao;

    protected void setDao(BaseDailyHourlyDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public List<T> getByUidAndCompleteMethod(String uid, int completeMethod, long startTime, long endTime) {
        return dao.getByUidAndMethod(uid, completeMethod, startTime, endTime);
    }

    @Override
    public long getLatestTime(String uid) {
        return dao.getLatestTime(uid);
    }

    @Override
    public int getAverageData(String uid, String colName, int methodCode, long startTime, long endTime) {
        List<Double> store = dao.getAverageList(uid, colName, methodCode, startTime, endTime);
        int n = store.size();
        List<Double> weights = getWeights(n);
        double res = 0;
        for (int i = 0; i < n; i++) {
            res += store.get(0) * weights.get(0);
        }
        return (int) Math.round(res);
    }

    /**
     * 得到n个数的权重，权重加和为1
     * @param n 权重个数
     * @return 权重列表
     */
    private List<Double> getWeights(int n) {
        List<Double> res = new ArrayList<>();
        double y;
        for (int x = 1; x <= n; x++) {
            y = (2.0 / (n * (n + 1))) * x;
            res.add(y);
        }
        return res;
    }
}
