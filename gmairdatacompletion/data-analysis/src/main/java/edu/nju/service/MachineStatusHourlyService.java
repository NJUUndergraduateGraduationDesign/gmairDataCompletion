package edu.nju.service;

import edu.nju.bo.MachineStatusHourly;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 17:11
 * @description：
 */

public interface MachineStatusHourlyService {
    void saveMachineStatusHourlyList(List<MachineStatusHourly> list);

    void saveMachineStatusHourly(MachineStatusHourly status);
}
