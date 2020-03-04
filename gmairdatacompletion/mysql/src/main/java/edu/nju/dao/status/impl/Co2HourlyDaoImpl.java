package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.Co2HourlyDao;
import edu.nju.model.status.Co2Hourly;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:51
 * @description：
 */

@Repository
public class Co2HourlyDaoImpl extends BaseDailyHourlyDaoImpl<Co2Hourly> implements Co2HourlyDao {
}
