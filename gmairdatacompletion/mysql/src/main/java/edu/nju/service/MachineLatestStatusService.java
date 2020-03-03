package edu.nju.service;

import edu.nju.model.machine.MachineBasicInfo;
import edu.nju.model.machine.MachineLatestStatus;

import java.util.Date;
import java.util.List;

public interface MachineLatestStatusService {

    MachineLatestStatus findByUid(String uid);

    List<MachineLatestStatus> findAllLatestStatus();

    List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                           int offset, int pageSize);

    List<MachineBasicInfo> findByQueryCond(int isPower,
                                           int offset, int pageSize);

    List<MachineBasicInfo> findByQueryCond(int overCountGTE, int overCountLTE,
                                           int offset, int pageSize);

    List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                           int isPower,
                                           int offset, int pageSize);

    List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                           int overCountGTE, int overCountLTE,
                                           int offset, int pageSize);

    List<MachineBasicInfo> findByQueryCond(int overCountGTE, int overCountLTE,
                                           int isPower,
                                           int offset, int pageSize);

    List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                           int isPower,
                                           int overCountGTE, int overCountLTE,
                                           int offset, int pageSize);

    boolean add(MachineLatestStatus latestStatus);

    void update(MachineLatestStatus latestStatus);

}
