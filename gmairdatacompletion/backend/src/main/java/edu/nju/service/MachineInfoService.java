package edu.nju.service;

import edu.nju.dto.MachineBasicInfo;

/**
 * @author: Bright Chan
 * @date: 2020/3/1 13:20
 * @description: TODO
 */
public interface MachineInfoService {

    MachineBasicInfo getMachineBasicInfoByUid(String uid);

}
