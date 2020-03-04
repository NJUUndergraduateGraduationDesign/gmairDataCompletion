package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.HumidHourlyDao;
import edu.nju.model.status.HumidHourly;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:53
 * @description：
 */

@Repository
public class HumidHourlyDaoImpl extends BaseDailyHourlyDaoImpl<HumidHourly> implements HumidHourlyDao {
}
