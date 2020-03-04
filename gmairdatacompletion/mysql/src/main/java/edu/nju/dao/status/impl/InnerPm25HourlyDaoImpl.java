package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.InnerPm25HourlyDao;
import edu.nju.model.status.InnerPm25Hourly;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:54
 * @description：
 */

@Repository
public class InnerPm25HourlyDaoImpl extends BaseDailyHourlyDaoImpl<InnerPm25Hourly> implements InnerPm25HourlyDao {
}
