package edu.nju.mongo.repository;

import edu.nju.mongo.model.MachineV1Status;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 16:55
 * @description：repository of machineV1Status
 */

public interface MachineV1StatusRepository extends MongoRepository<MachineV1Status, String> {
}
