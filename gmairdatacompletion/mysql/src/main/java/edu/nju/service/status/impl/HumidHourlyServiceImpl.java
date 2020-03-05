package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.HumidHourlyDao;
import edu.nju.model.status.HumidHourly;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.HumidHourlyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:19
 * @description：
 */

@Transactional
@Service
public class HumidHourlyServiceImpl extends BaseDailyHourlyServiceImpl<HumidHourly> implements HumidHourlyService {
    @Resource
    public void setDao(BaseDailyHourlyDao<HumidHourly> humidHourlyDao) {
        super.setDao(humidHourlyDao);
    }
}
