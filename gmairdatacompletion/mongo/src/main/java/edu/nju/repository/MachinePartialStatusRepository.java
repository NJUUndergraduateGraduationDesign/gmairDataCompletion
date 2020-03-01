package edu.nju.repository;

import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV2Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 16:57
 * @description：repository of machinePartialStatus
 */

public interface MachinePartialStatusRepository extends MongoRepository<MachinePartialStatus, String> {
    List<MachinePartialStatus> findByUid(String uid);

    Page<MachinePartialStatus> findByUid(String uid, PageRequest createAt);

    @Query(value="{$and: [{'uid': ?0},{'createAt':{$gte: ?1,$lt: ?2}}]}",sort="{'createAt':1}")
    List<MachinePartialStatus> findByUid(String uid, long startTime, long endTime);

    /**
     * 找到某一台设备的最新一条记录
     * @param uid 设备uid
     * @return 设备信息
     */
    MachinePartialStatus findFirstByUidOrderByCreateAtDesc(String uid);

    MachinePartialStatus findFirstByUidOrderByCreateAt(String uid);
}
