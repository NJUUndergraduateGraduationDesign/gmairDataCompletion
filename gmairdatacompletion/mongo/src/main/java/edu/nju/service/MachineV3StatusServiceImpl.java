package edu.nju.service;

import edu.nju.model.MachineV3Status;
import edu.nju.repository.MachineV3StatusRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 17:03
 * @description： service implement of machineV3Status
 */

@Transactional
@Service
public class MachineV3StatusServiceImpl extends MachineCommonServiceImpl<MachineV3Status> implements MachineV3StatusService {
    @Resource
    MachineV3StatusRepository repository;

    @Resource
    void setMachineV3StatusRepository(MachineV3StatusRepository repository) {
        super.setRepository(repository);
    }
}
