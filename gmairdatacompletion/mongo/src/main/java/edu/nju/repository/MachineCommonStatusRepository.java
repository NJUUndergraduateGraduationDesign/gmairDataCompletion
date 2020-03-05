package edu.nju.repository;

import edu.nju.model.MachineCommonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/5 14:41
 * @description：common repository of machineStatus
 */

public interface MachineCommonStatusRepository<T extends MachineCommonStatus> extends MongoRepository<T, String> {
    Page<T> findByUid(String uid, PageRequest createAt);

    @Query(value = "{$and: [{'uid': ?0},{'createAt':{$gte: ?1,$lt: ?2}}]}", sort = "{'createAt':1}")
    List<T> findByUid(String uid, long startTime, long endTime);

    T findFirstByUidOrderByCreateAtDesc(String uid);

    T findFirstByUidOrderByCreateAt(String uid);
}
