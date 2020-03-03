package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.InnerPm25DailyDao;
import edu.nju.model.status.InnerPm25Daily;
import edu.nju.service.InnerPm25DailyService;
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
public class InnerPm25DailyServiceImpl extends BaseDailyHourlyServiceImpl<InnerPm25Daily> implements InnerPm25DailyService {
    @Resource
    private InnerPm25DailyDao innerPm25DailyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<InnerPm25Daily> innerPm25DailyDao) {
        super.setDao(innerPm25DailyDao);
    }
}
