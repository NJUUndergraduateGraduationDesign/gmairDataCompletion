package edu.nju.dao.impl;

import edu.nju.dao.HumidHourlyDao;
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
