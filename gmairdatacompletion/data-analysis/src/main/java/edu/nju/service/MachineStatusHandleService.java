package edu.nju.service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 23:24
 * @description：machine status service
 */

public interface MachineStatusHandleService {
    /*
    将mongodb中的数据以小时和天为单位存入mysql
     */
    void handleAllData();

    void handleAllV2Data();

    void handleAllV3Data();

    void handleAllPartialData();

    void handleV2Data(List<String> uidList);

    void handleV3Data(List<String> uidList);

    void handlePartialData(List<String> uidList);
}
