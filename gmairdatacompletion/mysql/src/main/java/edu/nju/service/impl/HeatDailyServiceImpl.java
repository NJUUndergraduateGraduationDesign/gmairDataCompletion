package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.HeatDailyDao;
import edu.nju.model.status.HeatDaily;
import edu.nju.service.HeatDailyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:19
 * @description：
 */

@Transactional
@Service
public class HeatDailyServiceImpl extends BaseDailyHourlyServiceImpl<HeatDaily> implements HeatDailyService {
    @Resource
    private HeatDailyDao heatDailyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<HeatDaily> heatDailyDao) {
        super.setDao(heatDailyDao);
    }
}
