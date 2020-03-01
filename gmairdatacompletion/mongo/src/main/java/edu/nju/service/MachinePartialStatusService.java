package edu.nju.service;

import edu.nju.model.MachinePartialStatus;
import org.springframework.data.domain.Page;

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

    Page<MachinePartialStatus> fetchBatchByUid(String uid, int pageIndex, int pageSize);

    List<MachinePartialStatus> findByUid(String uid);

    void insertBatch(List<MachinePartialStatus> machinePartialStatuses);
}
