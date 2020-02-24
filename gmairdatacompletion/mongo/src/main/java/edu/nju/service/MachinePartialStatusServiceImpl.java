package edu.nju.service;

import edu.nju.model.MachinePartialStatus;
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

    @Override
    public List<String> getAllUids() {
        return mongoTemplate.query(MachinePartialStatus.class).distinct("uid").as(String.class).all();
    }
}
