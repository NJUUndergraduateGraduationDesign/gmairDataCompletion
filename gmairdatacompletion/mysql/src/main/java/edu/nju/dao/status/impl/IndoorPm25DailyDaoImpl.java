package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.IndoorPm25DailyDao;
import edu.nju.model.status.IndoorPm25Daily;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:53
 * @description：
 */

@Repository
public class IndoorPm25DailyDaoImpl extends BaseDailyHourlyDaoImpl<IndoorPm25Daily> implements IndoorPm25DailyDao {
}