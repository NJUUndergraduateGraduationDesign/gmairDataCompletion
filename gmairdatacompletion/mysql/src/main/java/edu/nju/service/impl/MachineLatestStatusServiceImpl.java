package edu.nju.service.impl;

import edu.nju.dao.MachineLatestStatusDao;
import edu.nju.model.machine.MachineBasicInfo;
import edu.nju.model.machine.MachineLatestStatus;
import edu.nju.service.MachineLatestStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 13:10
 * @description: TODO
 */

@Service
@Transactional
public class MachineLatestStatusServiceImpl implements MachineLatestStatusService {

    @Autowired
    private MachineLatestStatusDao machineLatestStatusDao;

    @Override
    public MachineLatestStatus findByUid(String uid) {
        return machineLatestStatusDao.get(uid);
    }

    @Override
    public List<MachineLatestStatus> findAllLatestStatus() {
        return machineLatestStatusDao.getAll();
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int offset, int pageSize) {
        return machineLatestStatusDao.findByQueryCond(offset, pageSize);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int offset, int pageSize) {
        return machineLatestStatusDao.findByQueryCond(createTimeGTE, createTimeLTE, offset, pageSize);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int isPower,
                                                  int offset, int pageSize) {
        return machineLatestStatusDao.findByQueryCond(isPower, offset, pageSize);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int overCountGTE, int overCountLTE,
                                                  int offset, int pageSize) {
        return machineLatestStatusDao.findByQueryCond(overCountGTE, overCountLTE, offset, pageSize);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int isPower,
                                                  int offset, int pageSize) {
        return machineLatestStatusDao.findByQueryCond(createTimeGTE, createTimeLTE,
                                                        isPower, offset, pageSize);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int overCountGTE, int overCountLTE,
                                                  int offset, int pageSize) {
        return machineLatestStatusDao.findByQueryCond(createTimeGTE, createTimeLTE,
                                                        overCountGTE, overCountLTE, offset, pageSize);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int overCountGTE, int overCountLTE,
                                                  int isPower,
                                                  int offset, int pageSize) {
        return machineLatestStatusDao.findByQueryCond(overCountGTE, overCountLTE, isPower, offset, pageSize);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int isPower,
                                                  int overCountGTE, int overCountLTE,
                                                  int offset, int pageSize) {
        return machineLatestStatusDao.findByQueryCond(createTimeGTE, createTimeLTE, isPower,
                                overCountGTE, overCountLTE, offset, pageSize);
    }

    @Override
    public boolean add(MachineLatestStatus latestStatus) {
        return machineLatestStatusDao.add(latestStatus).equals(latestStatus.getUid());
    }

    @Override
    public void update(MachineLatestStatus latestStatus) {
        machineLatestStatusDao.update(latestStatus);
    }
}
