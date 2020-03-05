package edu.nju.service;

import edu.nju.model.MachinePartialStatus;
import edu.nju.repository.MachinePartialStatusRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 17:02
 * @description：service implement of machinePartialStatus
 */

@Transactional
@Service
public class MachinePartialStatusServiceImpl extends MachineCommonServiceImpl<MachinePartialStatus> implements MachinePartialStatusService {
    @Resource
    MachinePartialStatusRepository repository;

    @Resource
    void setMachinePartialStatusRepository(MachinePartialStatusRepository repository) {
        super.setRepository(repository);
    }
}
