package edu.nju.dao.impl;

import edu.nju.dao.HeatHourlyDao;
import edu.nju.model.status.HeatHourly;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:53
 * @description：
 */

@Repository
public class HeatHourlyDaoImpl extends BaseDailyHourlyDaoImpl<HeatHourly> implements HeatHourlyDao {
}
