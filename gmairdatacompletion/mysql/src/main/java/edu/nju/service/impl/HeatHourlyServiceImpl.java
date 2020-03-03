package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.HeatHourlyDao;
import edu.nju.model.status.HeatDaily;
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
public class HeatHourlyServiceImpl extends BaseDailyHourlyServiceImpl<HeatDaily> implements HeatHourlyService {
    @Resource
    private HeatHourlyDao heatHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<HeatDaily> heatHourlyDao) {
        super.setDao(heatHourlyDao);
    }
}
