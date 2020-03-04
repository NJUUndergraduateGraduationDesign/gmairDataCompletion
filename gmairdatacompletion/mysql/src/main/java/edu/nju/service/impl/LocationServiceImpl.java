package edu.nju.service.impl;

import edu.nju.dao.LocationDao;
import edu.nju.model.Location;
import edu.nju.model.statistic.UserLocation;
import edu.nju.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<UserLocation> getNationalUserLocation() {
        return locationDao.getNationalUserLocation();
    }

    @Override
    public List<UserLocation> getProvincialUserLocation(String province) {
        return locationDao.getProvincialUserLocation(province);
    }
}
