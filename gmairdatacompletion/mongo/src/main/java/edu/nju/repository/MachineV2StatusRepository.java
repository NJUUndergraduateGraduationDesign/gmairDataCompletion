package edu.nju.repository;

import edu.nju.model.MachineV2Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:15
 * @description：repository of machineStatus
 */

public interface MachineV2StatusRepository extends MongoRepository<MachineV2Status, String> {
    List<MachineV2Status> findByUid(String uid);

    Page<MachineV2Status> findByUid(String uid, PageRequest createAt);

    @Query(value="{$and: [{'uid': ?0},{'createAt':{$gte: ?1,$lt: ?2}}]}",sort="{'createAt':1}")
    List<MachineV2Status> findByUid(String uid, long startTime, long endTime);

    /**
     * 找到某一台设备的最新一条记录
     * @param uid 设备uid
     * @return 设备信息
     */
    MachineV2Status findFirstByUidOrderByCreateAtDesc(String uid);

    MachineV2Status findFirstByUidOrderByCreateAt(String uid);

    @Aggregation("{ '$project': { '_id' : '$uid' } }")
    List<String> findAllUids();
}
