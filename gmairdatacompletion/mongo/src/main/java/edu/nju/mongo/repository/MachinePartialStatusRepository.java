package edu.nju.mongo.repository;

import edu.nju.mongo.model.MachinePartialStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 16:57
 * @description：repository of machinePartialStatus
 */

public interface MachinePartialStatusRepository extends MongoRepository<MachinePartialStatus, String> {
}
