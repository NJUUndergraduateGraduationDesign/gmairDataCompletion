package edu.nju.service.status.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.status.VolumeDailyDao;
import edu.nju.model.status.VolumeDaily;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.VolumeDailyService;
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
