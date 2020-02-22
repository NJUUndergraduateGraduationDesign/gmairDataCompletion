package edu.nju.mongo.service;

import edu.nju.mongo.model.MachineV2Status;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:16
 * @description：service interface of machineStatus
 */
public interface MachineV2StatusService {
    long count();

    List<MachineV2Status> findByUid(String uid);

    List<MachineV2Status> findAll();

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
}
