package edu.nju.service.status.impl;

import edn.nju.util.TimeUtil;
import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.model.monthly.MostOpenHour;
import edu.nju.model.status.PowerHourly;
import edu.nju.service.impl.BaseDailyHourlyServiceImpl;
import edu.nju.service.status.PowerHourlyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:21
 * @description：
 */

@Transactional
@Service
public class PowerHourlyServiceImpl extends BaseDailyHourlyServiceImpl<PowerHourly> implements PowerHourlyService {
    @Resource
    public void setDao(BaseDailyHourlyDao<PowerHourly> powerHourlyDao) {
        super.setDao(powerHourlyDao);
    }

    private static final int HOUR_PER_DAY = 24;

    @Override
    public MostOpenHour getMostOpenHour(String uid, int methodCode, long startTime, long endTime) {
        List<PowerHourly> powerHourlyList = getByUidAndCompleteMethod(uid, methodCode, startTime, endTime);
        int mostOpenHourGTE = 0;
        double mostOpenHourMinutesCount = 0;
        for (int i = 0; i < HOUR_PER_DAY; i++) {
            int finalI = i;
            List<PowerHourly> subList = powerHourlyList.stream()
                    .filter(e -> TimeUtil.getHourOfTime(e.getCreateAt()) == finalI)
                    .collect(Collectors.toList());
            OptionalDouble averageOptional = subList.stream().mapToDouble(PowerHourly::getPowerOnMinute).average();
            if (averageOptional.isPresent() && averageOptional.getAsDouble() > mostOpenHourMinutesCount) {
                mostOpenHourGTE = i;
                mostOpenHourMinutesCount = averageOptional.getAsDouble();
            }
        }
        return new MostOpenHour(mostOpenHourGTE, mostOpenHourGTE + 1, mostOpenHourMinutesCount);
    }
}
