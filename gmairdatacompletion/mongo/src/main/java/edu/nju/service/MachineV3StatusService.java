package edu.nju.service;

import edu.nju.model.MachineV2Status;
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
    获取所有的Uid
    */
    List<String> getAllUids();

    /**
     * 根据uid得到该设备最新的一条记录
     *
     * @param uid 设备uid
     * @return 最新的一条记录
     */
    MachineV3Status getLatestRecord(String uid);

    /*
    根据uid返回该设备最早记录的时间
    */
    long getStartTimeByUid(String uid);

    /*
    根据uid返回该设备最近记录的时间
     */
    long getLatestTimeByUid(String uid);


    Page<MachineV3Status> fetchBatchByUid(String uid, int pageIndex, int pageSize);

    /*
    根据uid返回该设备指定时间戳内的记录
    */
    List<MachineV3Status> fetchBatchByUid(String uid, long start, long end);

    /*
    批量插入
     */
    void insertBatch(List<MachineV3Status> machineV3Statuses);
}
