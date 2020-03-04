package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.VolumeHourlyDao;
import edu.nju.model.status.VolumeHourly;
import edu.nju.service.VolumeHourlyService;
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
public class VolumeHourlyServiceImpl extends BaseDailyHourlyServiceImpl<VolumeHourly> implements VolumeHourlyService {
    @Resource
    private VolumeHourlyDao volumeHourlyDaoImpl;

    @Resource
    public void setDao(BaseDailyHourlyDao<VolumeHourly> volumeHourlyDao) {
        super.setDao(volumeHourlyDao);
    }
}