package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.Co2DailyDao;
import edu.nju.model.status.Co2Daily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.Co2DailyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 1:25
 * @description：
 */

@Transactional
@Service
public class Co2DailyServiceImpl extends BaseDailyHourlyServiceImpl<Co2Daily> implements Co2DailyService {
    @Resource
    public void setDao(BaseDailyHourlyDao<Co2Daily> co2DailyDao) {
        super.setDao(co2DailyDao);
    }
}
