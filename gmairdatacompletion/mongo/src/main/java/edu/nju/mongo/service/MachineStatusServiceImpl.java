package edu.nju.mongo.service;

import edu.nju.mongo.model.MachineStatus;
import edu.nju.mongo.repository.MachineStatusRepository;
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
public class MachineStatusServiceImpl implements MachineStatusService {
    @Resource
    private MachineStatusRepository machineStatusRepository;

    @Override
    public long count() {
        return machineStatusRepository.count();
    }

    @Override
    public List<MachineStatus> findByUid(String uid) {
        return machineStatusRepository.findByUid(uid);
    }

    @Override
    public List<MachineStatus> findAll() {
        return machineStatusRepository.findAll();
    }

    @Override
    public MachineStatus findById(String id) {
        Optional<MachineStatus> machineStatusOptional = machineStatusRepository.findById(id);
        return machineStatusOptional.isPresent() ? machineStatusOptional.get() : null;
    }

    @Override
    public void create(MachineStatus machineStatus) {
        machineStatusRepository.insert(machineStatus);
    }

    @Override
    public void saveOrUpdate(MachineStatus machineStatus) {
        machineStatusRepository.save(machineStatus);
    }
}
