package edu.nju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/7 21:01
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReportDTO {
    private int openDaysCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date mostOpenDay;
    private int mostOpenHourGTE;
    private int mostOpenHourLTE;
    private String mostUseMode;
    private double defeatUserPercent;
    private int categoryEnvironment;
}
