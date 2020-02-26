package edu.nju.service;

import edu.nju.model.MachinePartialStatus;
import edu.nju.repository.MachinePartialStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 17:02
 * @description：service implement of machinePartialStatus
 */

@Service
public class MachinePartialStatusServiceImpl implements MachinePartialStatusService {
    @Resource
    MongoTemplate mongoTemplate;
    @Resource
    MachinePartialStatusRepository machinePartialStatusRepository;

    @Override
    public List<String> getAllUids() {
        return mongoTemplate.query(MachinePartialStatus.class).distinct("uid").as(String.class).all();
    }

    @Override
    public Page<MachinePartialStatus> fetchBatchByUid(String uid, int pageIndex, int pageSize) {
        return machinePartialStatusRepository.findByUid(uid, PageRequest.of(pageIndex, pageSize, Sort.by("createAt").ascending()));
    }

    @Override
    public void insertBatch(List<MachinePartialStatus> machinePartialStatuses) {
        machinePartialStatusRepository.insert(machinePartialStatuses);
    }
}
