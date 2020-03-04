package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.PowerDailyDao;
import edu.nju.model.status.PowerDaily;
import edu.nju.service.PowerDailyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:21
 * @description：
 */

@Transactional
@Service
public class PowerDailyServiceImpl extends BaseDailyHourlyServiceImpl<PowerDaily> implements PowerDailyService {
    @Resource
    private PowerDailyDao powerDailyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<PowerDaily> powerDailyDao) {
        super.setDao(powerDailyDao);
    }
}