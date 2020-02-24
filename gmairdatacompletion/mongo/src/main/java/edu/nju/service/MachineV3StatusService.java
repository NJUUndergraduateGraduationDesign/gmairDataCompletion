package edu.nju.service;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 17:01
 * @description：service interface of machineV3Status
 */

public interface MachineV3StatusService {
    /*
    获取所有的Uid:对象中只有uid有值
    */
    List<String> getAllUids();
}
