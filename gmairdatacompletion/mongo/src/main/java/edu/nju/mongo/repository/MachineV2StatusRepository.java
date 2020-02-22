package edu.nju.mongo.repository;

import edu.nju.mongo.model.MachineV2Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:15
 * @description：repository of machineStatus
 */

public interface MachineV2StatusRepository extends MongoRepository<MachineV2Status, String> {
    List<MachineV2Status> findByUid(String uid);
}
