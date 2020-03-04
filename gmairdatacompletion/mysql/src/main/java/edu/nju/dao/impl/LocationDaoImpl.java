package edu.nju.dao.impl;

import edu.nju.dao.LocationDao;
import edu.nju.model.Location;
import edu.nju.model.statistic.UserLocation;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 20:09
 * @description: TODO
 */

@Repository
public class LocationDaoImpl extends BaseDaoImpl<Location> implements LocationDao {
    @Override
    public List<UserLocation> getNationalUserLocation() {
        String hql = "select new edu.nju.model.statistic.UserLocation(l.provinceName, count(u.cityId)) " +
                "from User u, Location l where " +
                "u.dataType <> -1 and u.cityId = l.cityId group by l.provinceName";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<UserLocation> getProvincialUserLocation(String province) {
        String hql = "select new edu.nju.model.statistic.UserLocation(l.cityName, count(u.cityId)) " +
                "from User u, Location l where " +
                "u.dataType <> -1 and l.provinceName=?1 and u.cityId = l.cityId group by l.cityName";
        Query query = getSession().createQuery(hql);
        query.setParameter(1, province);
        return query.list();
    }
}
