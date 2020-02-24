package edu.nju.service;

import edu.nju.model.MachineV2Status;
import edu.nju.repository.MachineV2StatusRepository;
import edu.nju.repository.dto.UidOnly;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<MachineV2Status> findByUid(String uid) {
        return machineV2StatusRepository.findByUid(uid);
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

    @Override
    public long getStartTimeByUid(String uid) {
        MachineV2Status machineV2Status = machineV2StatusRepository.findFirstByUidOrderByCreateAt(uid);
        return machineV2Status==null?0:machineV2Status.getCreateAt();
    }

    @Override
    public List<MachineV2Status> fetchBatchByUid(String uid, long start, long end) {
        return machineV2StatusRepository.findByUidAndCreateAtBetweenOrderByCreateAt(uid, start, end);
    }

    @Override
    public void insertBatch(List<MachineV2Status> machineV2Statuses) {
        machineV2StatusRepository.insert(machineV2Statuses);
    }

    @Override
    public List<String> getAllUids() {
        return machineV2StatusRepository.findAllUids();
    }
}
