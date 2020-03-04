package edu.nju.dao;

import edu.nju.model.machine.MachineBasicInfo;
import edu.nju.model.machine.MachineLatestStatus;

import java.util.Date;
import java.util.List;

public interface MachineLatestStatusDao extends BaseDao<MachineLatestStatus> {

    List<MachineBasicInfo> findByQueryCond(int offset, int pageSize);

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
}
