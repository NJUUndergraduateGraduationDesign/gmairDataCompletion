package edu.nju.mongo.repository;

import edu.nju.mongo.model.MachineV3Status;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 16:56
 * @description：repository of machineV3Status
 */

public interface MachineV3StatusRepository extends MongoRepository<MachineV3Status, String> {
}