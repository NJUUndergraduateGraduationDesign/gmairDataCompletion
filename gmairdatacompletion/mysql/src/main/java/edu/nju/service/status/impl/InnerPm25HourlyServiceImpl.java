package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.InnerPm25HourlyDao;
import edu.nju.model.status.InnerPm25Hourly;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.InnerPm25HourlyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:20
 * @description：
 */

@Transactional
@Service
public class InnerPm25HourlyServiceImpl extends BaseDailyHourlyServiceImpl<InnerPm25Hourly> implements InnerPm25HourlyService {
    @Resource
    public void setDao(BaseDailyHourlyDao<InnerPm25Hourly> innerPm25HourlyDao) {
        super.setDao(innerPm25HourlyDao);
    }
}
