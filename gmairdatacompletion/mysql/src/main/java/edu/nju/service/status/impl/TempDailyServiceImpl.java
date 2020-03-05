package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.TempDailyDao;
import edu.nju.model.status.TempDaily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.TempDailyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

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
