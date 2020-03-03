package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.ModeDailyDao;
import edu.nju.model.status.ModeDaily;
import edu.nju.service.ModeDailyService;
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
public class ModeDailyServiceImpl extends BaseDailyHourlyServiceImpl<ModeDaily> implements ModeDailyService {
    @Resource
    private ModeDailyDao modeDailyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<ModeDaily> modeDailyDao) {
        super.setDao(modeDailyDao);
    }
}
