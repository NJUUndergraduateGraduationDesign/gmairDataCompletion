package edu.nju.service;

import edu.nju.model.MachineV3Status;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 17:03
 * @description：service implement of machineV3Status
 */

@Service
public class MachineV3StatusServiceImpl implements MachineV3StatusService {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public List<String> getAllUids() {
        return mongoTemplate.query(MachineV3Status.class).distinct("uid").as(String.class).all();
    }
}
