package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.HumidDailyDao;
import edu.nju.model.status.HumidDaily;
import edu.nju.service.HumidDailyService;
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
public class HumidDailyServiceImpl extends BaseDailyHourlyServiceImpl<HumidDaily> implements HumidDailyService {
    @Resource
    private HumidDailyDao humidDailyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<HumidDaily> humidDailyDao) {
        super.setDao(humidDailyDao);
    }
}
