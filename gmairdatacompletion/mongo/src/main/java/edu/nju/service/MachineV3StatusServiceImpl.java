package edu.nju.service;

import edu.nju.model.MachineV3Status;
import edu.nju.repository.MachineV3StatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 17:03
 * @description： service implement of machineV3Status
 */

@Service
public class MachineV3StatusServiceImpl implements MachineV3StatusService {
    @Resource
    MongoTemplate mongoTemplate;
    @Resource
    MachineV3StatusRepository machineV3StatusRepository;

    @Override
    public List<String> getAllUids() {
        return mongoTemplate.query(MachineV3Status.class).distinct("uid").as(String.class).all();
    }

    @Override
    public Page<MachineV3Status> fetchBatchByUid(String uid, int pageIndex, int pageSize) {
        return machineV3StatusRepository.findByUid(uid, PageRequest.of(pageIndex, pageSize, Sort.by("createAt").ascending()));
    }

    @Override
    public void insertBatch(List<MachineV3Status> machineV3Statuses) {
        machineV3StatusRepository.insert(machineV3Statuses);
    }
}
