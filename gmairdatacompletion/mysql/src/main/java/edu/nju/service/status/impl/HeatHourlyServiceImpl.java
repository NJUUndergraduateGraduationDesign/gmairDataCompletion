package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.HeatHourlyDao;
import edu.nju.model.status.HeatHourly;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.HeatHourlyService;
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
public class HeatHourlyServiceImpl extends BaseDailyHourlyServiceImpl<HeatHourly> implements HeatHourlyService {
    @Resource
    private HeatHourlyDao heatHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<HeatHourly> heatHourlyDao) {
        super.setDao(heatHourlyDao);
    }
}
