package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.InnerPm25DailyDao;
import edu.nju.model.status.InnerPm25Daily;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:54
 * @description：
 */

@Repository
public class InnerPm25DailyDaoImpl extends BaseDailyHourlyDaoImpl<InnerPm25Daily> implements InnerPm25DailyDao {
}
