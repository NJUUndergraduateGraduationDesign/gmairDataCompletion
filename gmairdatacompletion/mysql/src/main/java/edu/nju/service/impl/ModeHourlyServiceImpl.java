package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.ModeHourlyDao;
import edu.nju.model.status.ModeDaily;
import edu.nju.service.ModeHourlyService;
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
public class ModeHourlyServiceImpl extends BaseDailyHourlyServiceImpl<ModeDaily> implements ModeHourlyService {
    @Resource
    private ModeHourlyDao modeHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<ModeDaily> modeHourlyDao) {
        super.setDao(modeHourlyDao);
    }
}
