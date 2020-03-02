package edu.nju.service;

import edu.nju.bo.MachineV2StatusDaily;
import edu.nju.bo.MachineV2StatusHourly;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 17:11
 * @description：
 */

public interface MachineStatusDBService {
    void saveMachineStatusHourlyList(List<MachineV2StatusHourly> list);

    void saveMachineStatusHourly(MachineV2StatusHourly status);

    void saveMachineStatusDailyList(List<MachineV2StatusDaily> list);

    void saveMachineStatusDaily(MachineV2StatusDaily status);
}
