package edu.nju.service.impl;

import edu.nju.model.Location;
import edu.nju.model.User;
import edu.nju.model.machine.MachineBasicInfo;
import edu.nju.model.machine.MachineLatestStatus;
import edu.nju.service.LocationService;
import edu.nju.service.MachineInfoService;
import edu.nju.service.MachineLatestStatusService;
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
    private MachineLatestStatusService machineLatestStatusService;
    @Resource
    private LocationService locationService;

    @Override
    public MachineBasicInfo getMachineBasicInfoByUid(User user) {
        MachineBasicInfo res = new MachineBasicInfo();

        res.setUid(user.getUid());
        res.setBindTime(user.getBindTime());
        res.setCodeValue(user.getCodeValue());

        Location location = locationService.findByCityId(user.getCityId());
        res.setCity(location.getCityName());

        MachineLatestStatus latestStatus = machineLatestStatusService.findByUid(user.getUid());
        if (latestStatus != null) {
            res.setHeat(latestStatus.getHeat() > 0 ? 1 : 0);
            res.setIsPower(latestStatus.getPower());
            res.setMode(latestStatus.getMode());
            res.setOverCount(latestStatus.getOverCount());
        }
        else {
            res.setHeat(-1);
            res.setIsPower(-1);
            res.setMode(-1);
            res.setOverCount(-1);
        }

        return res;
    }
}
