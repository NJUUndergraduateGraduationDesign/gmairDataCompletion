package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.TempHourlyDao;
import edu.nju.model.status.TempHourly;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:56
 * @description：
 */

@Repository
public class TempHourlyDaoImpl extends BaseDailyHourlyDaoImpl<TempHourly> implements TempHourlyDao {
}
