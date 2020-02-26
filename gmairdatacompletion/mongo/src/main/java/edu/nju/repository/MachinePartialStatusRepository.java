package edu.nju.repository;

import edu.nju.model.MachinePartialStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 16:57
 * @description：repository of machinePartialStatus
 */

public interface MachinePartialStatusRepository extends MongoRepository<MachinePartialStatus, String> {
    Page<MachinePartialStatus> findByUid(String uid, PageRequest createAt);
}
