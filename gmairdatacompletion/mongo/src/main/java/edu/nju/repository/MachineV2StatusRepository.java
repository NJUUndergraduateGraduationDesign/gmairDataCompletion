package edu.nju.repository;

import edu.nju.model.MachineV2Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:15
 * @description：repository of machineStatus
 */

public interface MachineV2StatusRepository extends MongoRepository<MachineV2Status, String> {
    List<MachineV2Status> findByUid(String uid);

    Page<MachineV2Status> findByUid(String uid, PageRequest createAt);

    List<MachineV2Status> findByUidAndCreateAtBetweenOrderByCreateAt(String uid, long from, long to);

    MachineV2Status findFirstByUidOrderByCreateAt(String uid);

    @Aggregation("{ '$project': { '_id' : '$uid' } }")
    List<String> findAllUids();
}
