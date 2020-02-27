package edu.nju.method.impl;

import edn.nju.enums.CompleteMethodEnum;
import edu.nju.method.UsePrevious;
import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV1Status;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsePreviousImpl implements UsePrevious {

    private static final double v1Interval   = 5000.0;
    private static final double v1Bias       = 2000.0;  //可优化
    private static final double v2v3Interval = 30000.0;
    private static final double v2v3Bias     = 10000.0;

    @Override
    public List<MachinePartialStatus> partialUsePrevious(List<MachinePartialStatus> selectedData) {
        return null;
    }

    @Override
    public List<MachineV1Status> v1UsePrevious(List<MachineV1Status> selectedData) {
        return null;
    }

    @Override
    public List<MachineV2Status> v2UsePrevious(List<MachineV2Status> selectedData) {
        List<MachineV2Status> res = new ArrayList<>();
        for (int i = 0; i < selectedData.size() - 1; i++) {
            MachineV2Status current = selectedData.get(i);
            MachineV2Status next = selectedData.get(i + 1);

            long interval = next.getCreateAt() - current.getCreateAt();
            //计算两个时间点之前的时间间隔中可以插入多少数据
            int missingCount = (int) Math.ceil(interval / (v2v3Interval + v2v3Bias)) - 1;
            //计算插入数据的时间点
            int insertInterval = missingCount > 0 ? (int) interval / (missingCount + 1) : 0;
            long createTime = current.getCreateAt() + insertInterval;
            for (int j = 0; j < missingCount; j++) {
                createTime = createTime + j * insertInterval;

                MachineV2Status createdData = new MachineV2Status(
                        current.getUid(),
                        current.getPm2_5(),
                        current.getTemp(),
                        current.getHumid(),
                        current.getCo2(),
                        current.getVolume(),
                        current.getPower(),
                        current.getMode(),
                        current.getHeat(),
                        current.getLight(),
                        current.getLock(),
                        current.isBlockFlag(),
                        createTime,
                        CompleteMethodEnum.USE_PREVIOUS.getCode()
                );
                res.add(createdData);
            }
        }
        return res;
    }

    @Override
    public List<MachineV3Status> v3UsePrevious(List<MachineV3Status> selectedData) {
        List<MachineV3Status> res = new ArrayList<>();
        for (int i = 0; i < selectedData.size() - 1; i++) {
            MachineV3Status current = selectedData.get(i);
            MachineV3Status next = selectedData.get(i + 1);

            long interval = next.getCreateAt() - current.getCreateAt();
            //计算两个时间点之前的时间间隔中可以插入多少数据
            int missingCount = (int) Math.ceil(interval / (v2v3Interval + v2v3Bias)) - 1;
            //计算插入数据的时间点
            int insertInterval = missingCount > 0 ? (int) interval / (missingCount + 1) : 0;
            long createTime = current.getCreateAt() + insertInterval;
            for (int j = 0; j < missingCount; j++) {
                createTime = createTime + j * insertInterval;

                MachineV3Status createdData = new MachineV3Status(
                        current.getUid(),
                        current.getPm2_5a(),
                        current.getPm2_5b(),
                        current.getTempIndoor(),
                        current.getTempOutdoor(),
                        current.getHumidity(),
                        current.getCo2(),
                        current.getStatus(),
                        current.getMode(),
                        current.getVolume(),
                        current.getHeat(),
                        current.getLight(),
                        current.getChildlock(),
                        createTime,
                        CompleteMethodEnum.USE_PREVIOUS.getCode()
                );
                res.add(createdData);
            }
        }
        return res;
    }
}