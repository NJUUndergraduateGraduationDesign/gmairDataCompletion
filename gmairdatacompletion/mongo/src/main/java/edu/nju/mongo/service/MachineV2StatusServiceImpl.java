package edu.nju.mongo.service;

import edu.nju.mongo.model.MachineV2Status;
import edu.nju.mongo.repository.MachineV2StatusRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:16
 * @description：service implement of machineStatus
 */

@Service
public class MachineV2StatusServiceImpl implements MachineV2StatusService {
    @Resource
    private MachineV2StatusRepository machineV2StatusRepository;

    @Override
    public long count() {
        return machineV2StatusRepository.count();
    }

    @Override
    public List<MachineV2Status> findByUid(String uid) {
        return machineV2StatusRepository.findByUid(uid);
    }

    @Override
    public List<MachineV2Status> findAll() {
        return machineV2StatusRepository.findAll();
    }

    @Override
    public MachineV2Status findById(String id) {
        Optional<MachineV2Status> machineV2StatusOptional = machineV2StatusRepository.findById(id);
        return machineV2StatusOptional.isPresent() ? machineV2StatusOptional.get() : null;
    }

    @Override
    public void create(MachineV2Status machineV2Status) {
        machineV2StatusRepository.insert(machineV2Status);
    }

    @Override
    public void saveOrUpdate(MachineV2Status machineV2Status) {
        machineV2StatusRepository.save(machineV2Status);
    }
}
