package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.HumidHourlyDao;
import edu.nju.model.status.HumidDaily;
import edu.nju.service.HumidHourlyService;
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
public class HumidHourlyServiceImpl extends BaseDailyHourlyServiceImpl<HumidDaily> implements HumidHourlyService {
    @Resource
    private HumidHourlyDao humidHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<HumidDaily> humidHourlyDao) {
        super.setDao(humidHourlyDao);
    }
}
