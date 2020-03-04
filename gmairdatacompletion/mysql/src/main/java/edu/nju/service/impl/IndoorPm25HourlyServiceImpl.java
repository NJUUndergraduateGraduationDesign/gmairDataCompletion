package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.IndoorPm25HourlyDao;
import edu.nju.model.status.IndoorPm25Hourly;
import edu.nju.service.IndoorPm25HourlyService;
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
public class IndoorPm25HourlyServiceImpl extends BaseDailyHourlyServiceImpl<IndoorPm25Hourly> implements IndoorPm25HourlyService {
    @Resource
    private IndoorPm25HourlyDao indoorPm25HourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<IndoorPm25Hourly> indoorPm25HourlyDao) {
        super.setDao(indoorPm25HourlyDao);
    }
}