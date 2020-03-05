package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.ModeHourlyDao;
import edu.nju.model.status.ModeHourly;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.ModeHourlyService;
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
public class ModeHourlyServiceImpl extends BaseDailyHourlyServiceImpl<ModeHourly> implements ModeHourlyService {
    @Resource
    private ModeHourlyDao modeHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<ModeHourly> modeHourlyDao) {
        super.setDao(modeHourlyDao);
    }
}
