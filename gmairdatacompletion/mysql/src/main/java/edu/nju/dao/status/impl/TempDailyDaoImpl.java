package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.TempDailyDao;
import edu.nju.model.status.TempDaily;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:56
 * @description：
 */

@Repository
public class TempDailyDaoImpl extends BaseDailyHourlyDaoImpl<TempDaily> implements TempDailyDao {
}
