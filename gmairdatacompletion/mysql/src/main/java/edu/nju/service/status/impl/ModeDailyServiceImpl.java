package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.ModeDailyDao;
import edu.nju.model.status.ModeDaily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.ModeDailyService;
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
public class ModeDailyServiceImpl extends BaseDailyHourlyServiceImpl<ModeDaily> implements ModeDailyService {
    @Resource
    public void setDao(BaseDailyHourlyDao<ModeDaily> modeDailyDao) {
        super.setDao(modeDailyDao);
    }
}
