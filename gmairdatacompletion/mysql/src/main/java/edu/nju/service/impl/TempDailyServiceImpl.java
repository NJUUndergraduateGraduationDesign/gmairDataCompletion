package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.TempDailyDao;
import edu.nju.model.status.TempDaily;
import edu.nju.service.TempDailyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:22
 * @description：
 */

@Transactional
@Service
public class TempDailyServiceImpl extends BaseDailyHourlyServiceImpl<TempDaily> implements TempDailyService {
    @Resource
    private TempDailyDao tempDailyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<TempDaily> tempDailyDao) {
        super.setDao(tempDailyDao);
    }
}
