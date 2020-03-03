package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.PowerHourlyDao;
import edu.nju.model.status.PowerDaily;
import edu.nju.service.PowerHourlyService;
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
public class PowerHourlyServiceImpl extends BaseDailyHourlyServiceImpl<PowerDaily> implements PowerHourlyService {
    @Resource
    private PowerHourlyDao powerHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<PowerDaily> powerHourlyDao) {
        super.setDao(powerHourlyDao);
    }
}
