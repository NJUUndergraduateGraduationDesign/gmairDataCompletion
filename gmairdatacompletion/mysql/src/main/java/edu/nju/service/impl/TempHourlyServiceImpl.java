package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.TempHourlyDao;
import edu.nju.model.status.TempHourly;
import edu.nju.service.TempHourlyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:22
 * @description：
 */

@Transactional
@Service
public class TempHourlyServiceImpl extends BaseDailyHourlyServiceImpl<TempHourly> implements TempHourlyService {
    @Resource
    private TempHourlyDao tempHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<TempHourly> tempHourlyDao) {
        super.setDao(tempHourlyDao);
    }
}
