package edu.nju.service;

import edu.nju.dto.MachineBasicInfo;

/**
 * @author: Bright Chan
 * @date: 2020/3/1 13:20
 * @description: TODO
 */
public interface MachineInfoService {

    /**
     * 根据uid得到机器设备的基本信息
     * @param uid 设备uid
     * @return 机器设备的基本信息
     */
    MachineBasicInfo getMachineBasicInfoByUid(String uid);

}
