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

        double nextIndoorPm25 = lastOne.getIndoorPm25() * 2 - lastTwo.getIndoorPm25();
        res.setIndoorPm25(nextIndoorPm25 < 0 ? 0 : nextIndoorPm25);

        double nextInnerPm25 = lastOne.getInnerPm25() * 2 - lastTwo.getInnerPm25();
        res.setInnerPm25(nextInnerPm25 < 0 ? 0 : nextInnerPm25);

        double nextCo2 = lastOne.getCo2() * 2 - lastTwo.getCo2();
        res.setCo2(nextCo2 < 0 ? 0 : nextCo2);

        double nextHumid = lastOne.getHumid() * 2 - lastTwo.getHumid();
        res.setHumid(nextHumid < 0 ? 0 : nextHumid);

        double nextTemp = lastOne.getTemp() * 2 - lastTwo.getTemp();
        res.setTemp(nextTemp < 0 ? 0 : nextTemp);

        double nextVolume = lastOne.getVolume() * 2 - lastTwo.getVolume();
        res.setVolume(nextVolume < 0 ? 0 : nextVolume);
        return res;
    }

    @Override
    public MachineStatisticData usePreviousPredict(MachineStatisticData last) {
        return last;
    }
}
