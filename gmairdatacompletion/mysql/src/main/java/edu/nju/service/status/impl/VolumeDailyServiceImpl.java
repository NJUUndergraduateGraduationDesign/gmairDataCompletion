package edu.nju.service.status.impl;

import edn.nju.util.MyMath;
import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.VolumeDailyDao;
import edu.nju.model.status.VolumeDaily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.VolumeDailyService;
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
public class VolumeDailyServiceImpl extends BaseDailyHourlyServiceImpl<VolumeDaily> implements VolumeDailyService {
    @Resource
    private VolumeDailyDao volumeDailyDao;
    @Resource
    public void setDao(BaseDailyHourlyDao<VolumeDaily> volumeDailyDao) {
        super.setDao(volumeDailyDao);
    }

    @Override
    public int getAverageData(String uid, int methodCode, long startTime, long endTime) {
        List<Double> store = volumeDailyDao.getAverageList(uid, methodCode, startTime, endTime);
        return store == null ? 0 : MyMath.getAvgWithWeight(store);
    }
}
