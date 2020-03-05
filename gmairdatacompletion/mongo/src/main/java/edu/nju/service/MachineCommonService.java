package edu.nju.service;

import edu.nju.model.MachineCommonStatus;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/5 14:49
 * @description：common service of machineStatus
 */

public interface MachineCommonService<T extends MachineCommonStatus> {
    /**
     * 新建:
     * 如果没有指定id自动生成并插入;
     * 如果有id且数据库不存在相同id则插入;
     * 如果有id且数据库存在相同id则抛出运行时异常;
     *
     * @param t
     */
    void create(T t);

    /**
     * 新建或更新:
     * 如果没有指定id则自动生成并插入;
     * 如果有id且数据库不存在相同id则插入;
     * 如果有id且数据库存在相同id则更新;
     *
     * @param t
     */
    void saveOrUpdate(T t);

    /**
     * @return 获取所有的Uid
     */
    List<String> getAllUids();

    /**
     * 根据uid返回该设备最早记录的时间
     *
     * @param uid
     * @return 最早记录的时间
     */
    long getStartTimeByUid(String uid);

    /**
     * 根据uid返回该设备最近记录的时间
     *
     * @param uid
     * @return 最近记录的时间
     */
    long getLatestTimeByUid(String uid);


    /**
     * 根据uid得到该设备最新的一条记录
     *
     * @param uid 设备uid
     * @return 最新的一条记录
     */
    T getLatestRecord(String uid);

    /**
     * 根据uid返回该设备指定时间戳内的记录
     *
     * @param uid
     * @param start
     * @param end
     * @return 设备状态记录list
     */
    List<T> fetchBatchByUid(String uid, long start, long end);

    /**
     * 根据uid分页查询设备状态记录
     *
     * @param uid
     * @param pageIndex
     * @param pageSize
     * @return 设备状态记录page
     */
    Page<T> fetchBatchByUid(String uid, int pageIndex, int pageSize);

    /**
     * 根据uid返回该设备指定时间戳间隔内的记录
     *
     * @param uid
     * @param startTime
     * @param endTime
     * @param timeInterval 间隔
     * @param timeBias     间隔误差
     * @return
     */
    List<T> fetchBatchByUid(String uid, long startTime, long endTime, long timeInterval, long timeBias);

    /**
     * 批量插入
     *
     * @param list
     */
    void insertBatch(List<T> list);
}
