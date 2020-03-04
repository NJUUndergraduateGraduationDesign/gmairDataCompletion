package edu.nju.service.impl;

import edu.nju.dao.LocationDao;
import edu.nju.model.Location;
import edu.nju.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 20:11
 * @description: TODO
 */

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public boolean addOneLocation(Location location) {
        return locationDao.add(location).equals(location.getCityId());
    }

    @Override
    public Location findByCityId(String cityId) {
        return locationDao.get(cityId);
    }
}
