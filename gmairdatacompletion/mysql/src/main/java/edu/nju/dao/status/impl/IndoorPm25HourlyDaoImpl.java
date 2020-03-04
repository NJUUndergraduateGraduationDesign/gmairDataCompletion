package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.IndoorPm25HourlyDao;
import edu.nju.model.status.IndoorPm25Hourly;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:54
 * @description：
 */

@Repository
public class IndoorPm25HourlyDaoImpl extends BaseDailyHourlyDaoImpl<IndoorPm25Hourly> implements IndoorPm25HourlyDao {
}
