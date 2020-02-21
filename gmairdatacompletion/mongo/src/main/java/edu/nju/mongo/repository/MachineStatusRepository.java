package edu.nju.mongo.repository;

import edu.nju.mongo.model.MachineStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:15
 * @description：repository of machineStatus
 */

public interface MachineStatusRepository extends MongoRepository<MachineStatus, String> {
    List<MachineStatus> findByUid(String uid);
}
