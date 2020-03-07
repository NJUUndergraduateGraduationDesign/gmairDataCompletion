package edu.nju.service.impl;

import edu.nju.bo.MachineStatisticData;
import edu.nju.service.MachineStatusPredictService;
import org.springframework.stereotype.Service;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 15:07
 * @description: TODO
 */

@Service
public class MachineStatusPredictServiceImpl implements MachineStatusPredictService {
    @Override
    public MachineStatisticData gradientPredict(MachineStatisticData lastTwo,
                                                      MachineStatisticData lastOne) {
        MachineStatisticData res = new MachineStatisticData();
        res.setIndoorPm25(lastOne.getIndoorPm25() * 2 - lastTwo.getIndoorPm25());
        res.setInnerPm25(lastOne.getInnerPm25() * 2 - lastTwo.getInnerPm25());
        res.setCo2(lastOne.getCo2() * 2 - lastTwo.getCo2());
        res.setHumid(lastOne.getHumid() * 2 - lastTwo.getHumid());
        res.setTemp(lastOne.getTemp() * 2 - lastTwo.getTemp());
        res.setVolume(lastOne.getVolume() * 2 - lastTwo.getVolume());
        return res;
    }

    @Override
    public MachineStatisticData usePreviousPredict(MachineStatisticData last) {
        return last;
    }
}
