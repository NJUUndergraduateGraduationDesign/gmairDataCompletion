package edu.nju.repository;

import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 16:56
 * @description：repository of machineV3Status
 */

public interface MachineV3StatusRepository extends MongoRepository<MachineV3Status, String> {
    Page<MachineV3Status> findByUid(String uid, PageRequest createAt);

    @Query(value="{$and: [{'uid': ?0},{'createAt':{$gte: ?1,$lt: ?2}}]}",sort="{'createAt':1}")
    List<MachineV3Status> findByUid(String uid, long startTime, long endTime);

    /**
     * 找到某一台设备的最新一条记录
     * @param uid 设备uid
     * @return 设备信息
     */
    MachineV3Status findFirstByUidOrderByCreateAtDesc(String uid);

    MachineV3Status findFirstByUidOrderByCreateAt(String uid);
}
