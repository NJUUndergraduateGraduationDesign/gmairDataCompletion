package edu.nju.service;

import edu.nju.bo.MachineV2StatusDaily;
import edu.nju.bo.MachineV2StatusHourly;
import edu.nju.bo.MachineV3StatusDaily;
import edu.nju.bo.MachineV3StatusHourly;
import edu.nju.model.status.InnerPm25Daily;
import edu.nju.model.status.InnerPm25Hourly;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 14:01
 * @description：transfer MachineXXStatus to MachineXXStatusHourly bo
 */

public interface MachineStatusAnalyzeService {
    /**
    将同一个uid,同一小时对应的原始数据统计为hourly数据
    返回该uid该小时内不同数据补全方法的hourly数据
     */
    List<MachineV2StatusHourly> analyzeV2(String uid, long startTime);

    /*
    将同一个uid,同一小时对应的原始数据统计为hourly数据
    返回该uid该小时内不同数据补全方法的hourly数据
    */
    List<MachineV3StatusHourly> analyzeV3(String uid, long startTime);

    /*
    将同一个uid,同一小时对应的原始数据统计为hourly数据
    返回该uid该小时内不同数据补全方法的hourly数据
    */
    List<InnerPm25Hourly> analyzePartial(String uid, long startTime);

    /*
    将同一个uid,一天内的hourly统计数据统计为daily数据
    返回该uid该天内不同数据补全方法的daily数据
     */
    List<MachineV2StatusDaily> v2HourlyToDaily(List<MachineV2StatusHourly> hourlyList, long startTime);

    /*
    将同一个uid,一天内的hourly统计数据统计为daily数据
    返回该uid该天内不同数据补全方法的daily数据
    */
    List<MachineV3StatusDaily> v3HourlyToDaily(List<MachineV3StatusHourly> hourlyList, long startTime);

    /*
    将同一个uid,一天内的hourly统计数据统计为daily数据
    返回该uid该天内不同数据补全方法的daily数据
    */
    List<InnerPm25Daily> partialHourlyToDaily(List<InnerPm25Hourly> hourlyList, long startTime);
}
