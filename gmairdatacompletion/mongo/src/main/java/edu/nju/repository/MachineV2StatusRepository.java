package edu.nju.repository;

import edu.nju.model.MachineV2Status;
import edu.nju.repository.dto.UidOnly;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:15
 * @description：repository of machineStatus
 */

public interface MachineV2StatusRepository extends MongoRepository<MachineV2Status, String> {
    List<MachineV2Status> findByUid(String uid);

    List<MachineV2Status> findByUidAndCreateAtBetweenOrderByCreateAt(String uid,long from,long to);

    MachineV2Status findFirstByUidOrderByCreateAt(String uid);

    @Aggregation("{ '$project': { '_id' : '$uid' } }")
    List<String> findAllUids();
}
