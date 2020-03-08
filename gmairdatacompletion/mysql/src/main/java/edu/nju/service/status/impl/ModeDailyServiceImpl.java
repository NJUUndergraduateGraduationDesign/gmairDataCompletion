package edu.nju.service.status.impl;

import edn.nju.enums.ModeEnum;
import edn.nju.util.TimeUtil;
import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.ModeDailyDao;
import edu.nju.model.monthly.MostUseMode;
import edu.nju.model.status.ModeDaily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.ModeDailyService;
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
public class ModeDailyServiceImpl extends BaseDailyHourlyServiceImpl<ModeDaily> implements ModeDailyService {
    @Resource
    public void setDao(BaseDailyHourlyDao<ModeDaily> modeDailyDao) {
        super.setDao(modeDailyDao);
    }

    @Resource
    private ModeDailyDao modeDailyDao;

    @Override
    public int getAutoMinutes(String uid, int methodCode, long startTime, long endTime) {
        return modeDailyDao.getAutoMinutes(uid,methodCode,startTime,endTime);
    }

    @Override
    public int getSleepMinutes(String uid, int methodCode, long startTime, long endTime) {
        return modeDailyDao.getSleepMinutes(uid,methodCode,startTime,endTime);
    }

    @Override
    public int getManualMinutes(String uid, int methodCode, long startTime, long endTime) {
        return modeDailyDao.getManualMinutes(uid,methodCode,startTime,endTime);
    }
}
