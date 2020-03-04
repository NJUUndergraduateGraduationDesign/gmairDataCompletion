package edu.nju.service;

import edu.nju.model.Location;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 20:10
 * @description: TODO
 */
public interface LocationService {

    boolean addOneLocation(Location location);

    Location findByCityId(String cityId);

}
