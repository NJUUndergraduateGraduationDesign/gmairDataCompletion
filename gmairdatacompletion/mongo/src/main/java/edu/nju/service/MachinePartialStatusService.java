package edu.nju.service;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 17:00
 * @description：service interface of machinePartialStatus
 */

public interface MachinePartialStatusService {
    /*
    获取所有的Uid:对象中只有uid有值
    */
    List<String> getAllUids();
}
