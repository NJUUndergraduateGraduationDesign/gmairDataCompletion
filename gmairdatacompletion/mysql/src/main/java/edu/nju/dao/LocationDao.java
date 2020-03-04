package edu.nju.dao;

import edu.nju.model.Location;
import edu.nju.model.statistic.UserLocation;

import java.util.Date;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 20:08
 * @description: TODO
 */
public interface LocationDao extends BaseDao<Location> {

    List<UserLocation> getNationalUserLocation();

    List<UserLocation> getProvincialUserLocation(String province);
}
