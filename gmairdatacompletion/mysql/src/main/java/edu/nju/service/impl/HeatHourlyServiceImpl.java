package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.HeatHourlyDao;
import edu.nju.model.status.HeatHourly;
import edu.nju.service.HeatHourlyService;
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
public class HeatHourlyServiceImpl extends BaseDailyHourlyServiceImpl<HeatHourly> implements HeatHourlyService {
    @Resource
    private HeatHourlyDao heatHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<HeatHourly> heatHourlyDao) {
        super.setDao(heatHourlyDao);
    }
}