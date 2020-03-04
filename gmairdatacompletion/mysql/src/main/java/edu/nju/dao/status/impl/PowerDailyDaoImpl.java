package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.PowerDailyDao;
import edu.nju.model.status.PowerDaily;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:55
 * @description：
 */

@Repository
public class PowerDailyDaoImpl extends BaseDailyHourlyDaoImpl<PowerDaily> implements PowerDailyDao {
}
