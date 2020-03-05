package edu.nju.service;

import edu.nju.model.MachineV2Status;
import edu.nju.repository.MachineV2StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:16
 * @description： service implement of machineStatus
 */


@Transactional
@Service
public class MachineV2StatusServiceImpl extends MachineCommonServiceImpl<MachineV2Status> implements MachineV2StatusService {
    @Resource
    MachineV2StatusRepository repository;

    @Resource
    void setMachineV2StatusRepository(MachineV2StatusRepository repository) {
        super.setRepository(repository);
    }
}
