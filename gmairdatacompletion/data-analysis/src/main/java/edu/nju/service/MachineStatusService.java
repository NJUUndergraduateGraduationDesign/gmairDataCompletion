package edu.nju.service;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 23:24
 * @description：machine status service
 */

public interface MachineStatusService {
    /*
    将mongodb中的数据以小时和天为单位存入mysql
     */
    void handleAllData();
}
