package edu.nju.service;

import edu.nju.model.MachineV2Status;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:16
 * @description： service interface of machineStatus
 */
public interface MachineV2StatusService {
    /*
    根据Id查询:如果不存在返回null
     */
    MachineV2Status findById(String id);

    /*
    新建:
    如果没有指定id自动生成并插入;
    如果有id且数据库不存在相同id则插入;
    如果有id且数据库存在相同id则抛出运行时异常;
     */
    void create(MachineV2Status machineV2Status);

    /*
    新建或更新:
    如果没有指定id则自动生成并插入;
    如果有id且数据库不存在相同id则插入;
    如果有id且数据库存在相同id则更新;
     */
    void saveOrUpdate(MachineV2Status machineV2Status);

    /*
    获取所有的Uid
     */
    List<String> getAllUids();

    /*
    根据uid返回该设备最早记录的时间
     */
    long getStartTimeByUid(String uid);

    /*
    根据uid返回该设备最近记录的时间
     */
    long getLatestTimeByUid(String uid);


    /**
     * 根据uid得到该设备最新的一条记录
     * @param uid 设备uid
     * @return 最新的一条记录
     */
    MachineV2Status getLatestRecord(String uid);

    /*
    根据uid返回该设备指定时间戳内的时间
     */
    List<MachineV2Status> fetchBatchByUid(String uid, long start, long end);

    Page<MachineV2Status> fetchBatchByUid(String uid, int pageIndex, int pageSize);

    List<MachineV2Status> fetchBatchByUid(String uid, long startTime, long endTime, long timeInterval, long timeBias);

    /*
        批量插入
         */
    void insertBatch(List<MachineV2Status> machineV2Statuses);
}
