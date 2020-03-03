package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.VolumeDailyDao;
import edu.nju.model.status.VolumeDaily;
import edu.nju.service.VolumeDailyService;
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
public class VolumeDailyServiceImpl extends BaseDailyHourlyServiceImpl<VolumeDaily> implements VolumeDailyService {
    @Resource
    private VolumeDailyDao volumeDailyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<VolumeDaily> volumeDailyDao) {
        super.setDao(volumeDailyDao);
    }
}
