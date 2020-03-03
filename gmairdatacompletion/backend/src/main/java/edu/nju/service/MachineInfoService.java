package edu.nju.service;

import edu.nju.model.User;
import edu.nju.model.machine.MachineBasicInfo;

/**
 * @author: Bright Chan
 * @date: 2020/3/1 13:20
 * @description: TODO
 */
public interface MachineInfoService {

    /**
     * 得到机器设备的基本信息
     * @param user 设备实例
     * @return 机器设备的基本信息
     */
    MachineBasicInfo getMachineBasicInfoByUid(User user);

}
