package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.InnerPm25HourlyDao;
import edu.nju.model.status.InnerPm25Hourly;
import edu.nju.service.InnerPm25HourlyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:20
 * @description：
 */

@Transactional
@Service
public class InnerPm25HourlyServiceImpl extends BaseDailyHourlyServiceImpl<InnerPm25Hourly> implements InnerPm25HourlyService {
    @Resource
    private InnerPm25HourlyDao innerPm25HourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<InnerPm25Hourly> innerPm25HourlyDao) {
        super.setDao(innerPm25HourlyDao);
    }
}
