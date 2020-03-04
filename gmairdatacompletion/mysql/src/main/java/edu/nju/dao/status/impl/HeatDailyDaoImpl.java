package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.HeatDailyDao;
import edu.nju.model.status.HeatDaily;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:53
 * @description：
 */

@Repository
public class HeatDailyDaoImpl extends BaseDailyHourlyDaoImpl<HeatDaily> implements HeatDailyDao {
}
