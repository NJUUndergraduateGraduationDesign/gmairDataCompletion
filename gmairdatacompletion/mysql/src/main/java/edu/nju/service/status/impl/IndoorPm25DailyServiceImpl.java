package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.IndoorPm25DailyDao;
import edu.nju.model.status.IndoorPm25Daily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.IndoorPm25DailyService;
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
public class IndoorPm25DailyServiceImpl extends BaseDailyHourlyServiceImpl<IndoorPm25Daily> implements IndoorPm25DailyService {
    @Resource
    private IndoorPm25DailyDao indoorPm25DailyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<IndoorPm25Daily> indoorPm25DailyDao) {
        super.setDao(indoorPm25DailyDao);
    }
}
