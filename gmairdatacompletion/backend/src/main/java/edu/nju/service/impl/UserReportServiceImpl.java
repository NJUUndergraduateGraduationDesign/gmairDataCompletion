package edu.nju.service.impl;

import com.google.common.collect.Lists;
import edn.nju.constant.Constant;
import edn.nju.enums.ModeEnum;
import edn.nju.util.TimeUtil;
import edu.nju.dto.MonthlyReportDTO;
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
import java.util.List;

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
        Date mostOpenDay = new Date(powerDaily.getCreateAt());
        double mostOpenDayHoursCount = TimeUtil.minuteToHour(powerDaily.getPowerOnMinute());

        int sleepMinutes = modeDailyServiceImpl.getSleepMinutes(uid, Constant.MachineData.BEST_METHOD, startTime, endTime);
        int autoMinutes = modeDailyServiceImpl.getAutoMinutes(uid, Constant.MachineData.BEST_METHOD, startTime, endTime);
        int manualMinutes = modeDailyServiceImpl.getManualMinutes(uid, Constant.MachineData.BEST_METHOD, startTime, endTime);
        int maxMinutes = Math.max(sleepMinutes, Math.max(autoMinutes, manualMinutes));
        String mostUseMode = "";
        if (sleepMinutes == maxMinutes) {
            mostUseMode = ModeEnum.SLEEP.getCName();
        } else if (autoMinutes == maxMinutes) {
            mostUseMode = ModeEnum.AUTO.getCName();
        } else if (manualMinutes == maxMinutes) {
            mostUseMode = ModeEnum.MANUAL.getCName();
        }
        double mostUseModeHoursCount = TimeUtil.minuteToHour(maxMinutes);

        double averagePm25 = indoorPm25DailyServiceImpl.getAverage(uid, Constant.MachineData.BEST_METHOD, startTime, endTime);
        List<String> uidList = indoorPm25DailyServiceImpl.getAllUids(Constant.MachineData.BEST_METHOD, startTime, endTime);
        List<Double> pm25List= Lists.newArrayList();
        for(String str:uidList){
            double pm25=indoorPm25DailyServiceImpl.getAverage(str, Constant.MachineData.BEST_METHOD, startTime, endTime);
            pm25List.add(pm25);
        }
        double defeatUserPercent=0;
        int smallThan= (int) pm25List.stream().filter(e->e<averagePm25).count();
        int allBesideMe = pm25List.size()-1;
        if(allBesideMe==0){
            defeatUserPercent=100;
        }else {
            defeatUserPercent = (1 - (double) smallThan / allBesideMe) * 100;
        }
        log.info("smallThan:{},allBesideMe:{},defeatUserPercent:{}",smallThan,allBesideMe,defeatUserPercent);
        return MonthlyReportDTO.builder()
                .openDaysCount(openDaysCount).mostOpenDay(mostOpenDay).mostOpenDayHoursCount(mostOpenDayHoursCount)
                .mostUseMode(mostUseMode).mostUseModeHoursCount(mostUseModeHoursCount)
                .pm25Average(averagePm25).defeatUserPercent(defeatUserPercent)
                .build();
    }
}
