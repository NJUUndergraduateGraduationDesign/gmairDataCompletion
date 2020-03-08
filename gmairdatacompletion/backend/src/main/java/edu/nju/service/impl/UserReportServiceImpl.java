package edu.nju.service.impl;

import edn.nju.constant.Constant;
import edn.nju.util.TimeUtil;
import edu.nju.dto.MonthlyReportDTO;
import edu.nju.model.monthly.DefeatUserPercent;
import edu.nju.model.monthly.MostOpenHour;
import edu.nju.model.monthly.MostUseMode;
import edu.nju.model.status.PowerDaily;
import edu.nju.service.UserReportService;
import edu.nju.service.status.IndoorPm25DailyService;
import edu.nju.service.status.ModeDailyService;
import edu.nju.service.status.PowerDailyService;
import edu.nju.service.status.PowerHourlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/7 21:19
 * @description：
 */

@Slf4j
@Service
public class UserReportServiceImpl implements UserReportService {
    @Resource
    IndoorPm25DailyService indoorPm25DailyServiceImpl;
    @Resource
    ModeDailyService modeDailyServiceImpl;
    @Resource
    PowerDailyService powerDailyServiceImpl;
    @Resource
    PowerHourlyService powerHourlyServiceImpl;

    @Override
    public MonthlyReportDTO getMonthlyReport(String uid) {
        //获取用户最近记录的时间
        long endTime = indoorPm25DailyServiceImpl.getLatestTime(uid);
        //获取用户最近记录时间的对应月开始时间
        long startOfThisMonth = TimeUtil.getStartOfThisMonth(new Date(endTime)).getTime();
        long endOfThisMonth = TimeUtil.getEndOfThisMonth(new Date(endTime)).getTime();
        return getMonthlyReport(uid, startOfThisMonth, endOfThisMonth);
    }

    private MonthlyReportDTO getMonthlyReport(String uid, long startTime, long endTime) {
        int openDaysCount = powerDailyServiceImpl.getOpenCount(uid, Constant.MachineData.BEST_METHOD, startTime, endTime);
        PowerDaily powerDaily = powerDailyServiceImpl.getMostOpenDay(uid, Constant.MachineData.BEST_METHOD, startTime, endTime);
        Date mostOpenDay = null;
        double mostOpenDayHoursCount = 0;
        if (powerDaily != null) {
            mostOpenDay = new Date(powerDaily.getCreateAt());
            mostOpenDayHoursCount = TimeUtil.minuteToHour(powerDaily.getPowerOnMinute());
        }

        MostUseMode mostUseMode = modeDailyServiceImpl.getMostUseMode(uid, Constant.MachineData.BEST_METHOD, startTime, endTime);

        DefeatUserPercent defeatUserPercent = indoorPm25DailyServiceImpl.getDefeatUserPercent(uid, Constant.MachineData.BEST_METHOD, startTime, endTime);

        MostOpenHour mostOpenHour = powerHourlyServiceImpl.getMostOpenHour(uid, Constant.MachineData.BEST_METHOD, startTime, endTime);

        return MonthlyReportDTO.builder()
                .openDaysCount(openDaysCount).mostOpenDay(mostOpenDay).mostOpenDayHoursCount(mostOpenDayHoursCount)
                .mostUseMode(mostUseMode.getMostUseMode()).mostUseModeHoursCount(mostUseMode.getMostUseModeHoursCount())
                .pm25Average(defeatUserPercent.getPm25Average()).defeatUserPercent(defeatUserPercent.getDefeatUserPercent())
                .mostOpenHourGTE(mostOpenHour.getMostOpenHourGTE()).mostOpenHourLTE(mostOpenHour.getMostOpenHourLTE())
                .mostOpenHourMinutesCount(mostOpenHour.getMostOpenHourMinutesCount())
                .build();
    }
}
