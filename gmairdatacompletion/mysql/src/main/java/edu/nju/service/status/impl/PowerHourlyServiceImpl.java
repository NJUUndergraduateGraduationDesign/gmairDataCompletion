package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.PowerHourlyDao;
import edu.nju.model.status.PowerHourly;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.PowerHourlyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:21
 * @description：
 */

@Transactional
@Service
public class PowerHourlyServiceImpl extends BaseDailyHourlyServiceImpl<PowerHourly> implements PowerHourlyService {
    @Resource
    private PowerHourlyDao powerHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<PowerHourly> powerHourlyDao) {
        super.setDao(powerHourlyDao);
    }
}
