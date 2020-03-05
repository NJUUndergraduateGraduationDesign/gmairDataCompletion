package edu.nju.service;

import edu.nju.bo.MachineV2StatusDaily;
import edu.nju.bo.MachineV2StatusHourly;
import edu.nju.bo.MachineV3StatusDaily;
import edu.nju.bo.MachineV3StatusHourly;
import edu.nju.model.status.InnerPm25Daily;
import edu.nju.model.status.InnerPm25Hourly;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 17:11
 * @description：
 */

@Transactional
public interface MachineStatusDBService {
    default void saveMachineV2StatusHourlyList(List<MachineV2StatusHourly> list){
        for (MachineV2StatusHourly status : list) {
            saveMachineV2StatusHourly(status);
        }
    }

    void saveMachineV2StatusHourly(MachineV2StatusHourly status);

    default void saveMachineV2StatusDailyList(List<MachineV2StatusDaily> list){
        for (MachineV2StatusDaily status : list) {
            saveMachineV2StatusDaily(status);
        }
    }

    void saveMachineV2StatusDaily(MachineV2StatusDaily status);

    default void saveMachineV3StatusHourlyList(List<MachineV3StatusHourly> list){
        for (MachineV3StatusHourly status : list) {
            saveMachineV3StatusHourly(status);
        }
    }

    void saveMachineV3StatusHourly(MachineV3StatusHourly status);

    default void saveMachineV3StatusDailyList(List<MachineV3StatusDaily> list){
        for (MachineV3StatusDaily status : list) {
            saveMachineV3StatusDaily(status);
        }
    }

    void saveMachineV3StatusDaily(MachineV3StatusDaily status);

    default void saveMachinePartialStatusHourlyList(List<InnerPm25Hourly> list){
        for (InnerPm25Hourly status : list) {
            saveMachinePartialStatusHourly(status);
        }
    }

    void saveMachinePartialStatusHourly(InnerPm25Hourly status);

    default void saveMachinePartialStatusDailyList(List<InnerPm25Daily> list){
        for (InnerPm25Daily status : list) {
            saveMachinePartialStatusDaily(status);
        }
    }

    void saveMachinePartialStatusDaily(InnerPm25Daily status);
}
