package edu.nju.service;

import edu.nju.dto.MonthlyReportDTO;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/7 21:18
 * @description：
 */

public interface UserReportService {
    MonthlyReportDTO getMonthlyReport(String uid);
}
