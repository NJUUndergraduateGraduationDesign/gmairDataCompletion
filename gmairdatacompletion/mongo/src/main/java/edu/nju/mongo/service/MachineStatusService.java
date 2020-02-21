package edu.nju.mongo.service;

import edu.nju.mongo.model.MachineStatus;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:16
 * @description：service interface of machineStatus
 */
public interface MachineStatusService {
    long count();

    List<MachineStatus> findByUid(String uid);

    List<MachineStatus> findAll();

    /*
    根据Id查询:如果不存在返回null
     */
    MachineStatus findById(String id);

    /*
    新建:
    如果没有指定id自动生成并插入;
    如果有id且数据库不存在相同id则插入;
    如果有id且数据库存在相同id则抛出运行时异常;
     */
    void create(MachineStatus machineStatus);

    /*
    新建或更新:
    如果没有指定id则自动生成并插入;
    如果有id且数据库不存在相同id则插入;
    如果有id且数据库存在相同id则更新;
     */
    void saveOrUpdate(MachineStatus machineStatus);
}
