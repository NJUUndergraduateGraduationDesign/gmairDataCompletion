package edu.nju.service;

import edu.nju.model.MachineV3Status;
import org.springframework.data.domain.Page;

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

    Page<MachineV3Status> fetchBatchByUid(String uid, int pageIndex, int pageSize);

    /*
    批量插入
     */
    void insertBatch(List<MachineV3Status> machineV2Statuses);
}
