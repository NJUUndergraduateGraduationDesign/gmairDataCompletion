package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.Co2HourlyDao;
import edu.nju.model.status.Co2Hourly;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.Co2HourlyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 17:16
 * @description：
 */

@Transactional
@Service
public class Co2HourlyServiceImpl extends BaseDailyHourlyServiceImpl<Co2Hourly> implements Co2HourlyService {
    @Resource
    private Co2HourlyDao co2HourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<Co2Hourly> co2HourlyDao) {
        super.setDao(co2HourlyDao);
    }
}
