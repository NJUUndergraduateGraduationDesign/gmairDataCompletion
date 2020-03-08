package edu.nju.service.impl;

import edu.nju.dto.MonthlyReportDTO;
import edu.nju.service.UserReportService;
import org.springframework.stereotype.Service;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/7 21:19
 * @description：
 */

@Service
public class UserReportServiceImpl implements UserReportService {
    @Override
    public MonthlyReportDTO getMonthlyReport(String uid) {
        long endTime=0;
        return getMonthlyReport(uid,0);
    }

    private MonthlyReportDTO getMonthlyReport(String uid, long startTime) {
        return new MonthlyReportDTO();
    }
}
