package edu.nju.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edn.nju.util.DoubleSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/7 21:01
 * @description：
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReportDTO {
    //月机器运行天数
    private int openDaysCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    //运行时间最长日期
    private Date mostOpenDay;
    //运行时间最长日运行时间小时数
    @JsonSerialize(using = DoubleSerializer.class)
    private double mostOpenDayHoursCount;

    //最常使用时段开始
    private int mostOpenHourGTE;
    //最常使用时段结束
    private int mostOpenHourLTE;
    //最常使用时段平均使用时间分钟数
    @JsonSerialize(using = DoubleSerializer.class)
    private double mostOpenHourMinutesCount;

    //最常使用模式
    private String mostUseMode;
    //最常使用模式小时数
    @JsonSerialize(using = DoubleSerializer.class)
    private double mostUseModeHoursCount;

    //平均室内pm25
    @JsonSerialize(using = DoubleSerializer.class)
    private double pm25Average;
    //平局试内pm25打败用户百分比(0-100)
    @JsonSerialize(using = DoubleSerializer.class)
    private double defeatUserPercent;

    private int categoryEnvironment;
}
