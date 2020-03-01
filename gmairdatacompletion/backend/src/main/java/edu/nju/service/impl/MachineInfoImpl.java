package edu.nju.service.impl;

import edn.nju.enums.MachineStatusTypeEnum;
import edu.nju.dto.MachineBasicInfo;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;
import edu.nju.model.User;
import edu.nju.service.MachineInfoService;
import edu.nju.service.MachineV2StatusService;
import edu.nju.service.MachineV3StatusService;
import edu.nju.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Bright Chan
 * @date: 2020/3/1 13:21
 * @description: TODO
 */

@Service
public class MachineInfoImpl implements MachineInfoService {
    @Resource
    private UserService userService;
    @Resource
    private MachineV2StatusService machineV2StatusService;
    @Resource
    private MachineV3StatusService machineV3StatusService;

    @Override
    public MachineBasicInfo getMachineBasicInfoByUid(String uid) {
        MachineBasicInfo res = new MachineBasicInfo();

        User user = userService.findByUid(uid);
        res.setUid(uid);
        res.setBindTime(user.getBindTime());
        res.setCodeValue(user.getCodeValue());
        res.setCity(user.getCityId());

        // TODO insert overCount.
        res.setOverCount(0);

        if (user.getDataType() == MachineStatusTypeEnum.MACHINE_V2_STATUS.getCode()) {
            MachineV2Status machineV2Status = machineV2StatusService.getLatestRecord(uid);
            res.setHeat(machineV2Status.getHeat() > 0 ? 1 : 0);
            res.setIsPower(machineV2Status.getPower());
            res.setMode(machineV2Status.getMode());
        }
        else if (user.getDataType() == MachineStatusTypeEnum.MACHINE_V3_STATUS.getCode()) {
            MachineV3Status machineV3Status = machineV3StatusService.getLatestRecord(uid);
            res.setHeat(machineV3Status.getHeat() > 0 ? 1 : 0);
            res.setIsPower(machineV3Status.getStatus());
            res.setMode(machineV3Status.getMode());
        }

        return res;
    }
}
