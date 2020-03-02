package edu.nju.method.impl;

import edn.nju.enums.CompleteMethodEnum;
import edu.nju.method.Mean;
import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeanImpl implements Mean {

    private static final double v2v3Interval = 30000.0;
    private static final double v2v3Bias     = 10000.0;

    @Override
    public List<MachinePartialStatus> partialMean(List<MachinePartialStatus> selectedData) {
        List<MachinePartialStatus> res = new ArrayList<>();
        for (int i = 0; i < selectedData.size() - 1; i++) {
            MachinePartialStatus current = selectedData.get(i);
            MachinePartialStatus next    = selectedData.get(i + 1);

            long interval = next.getCreateAt() - current.getCreateAt();
            //计算两个时间点之前的时间间隔中可以插入多少数据
            int missingCount = (int) Math.ceil(interval / (v2v3Interval + v2v3Bias)) - 1;
            //计算插入数据的时间点
            int insertInterval = missingCount > 0 ? (int) interval / (missingCount + 1) : 0;
            long createTime = current.getCreateAt() + insertInterval;
            for (int j = 0; j < missingCount; j++) {
                createTime = createTime + j * insertInterval;

                //需要手动调节的属性和blockFlag都不取平均值，直接取前一个数据的值
                MachinePartialStatus createdData = new MachinePartialStatus(
                        current.getUid(),
                        current.getName(),
                        (current.getData() + next.getData()) / 2,
                        current.isBlockFlag(),
                        createTime,
                        CompleteMethodEnum.MEAN.getCode()
                );
                res.add(createdData);
            }
        }
        return res;
    }

    @Override
    public List<MachineV2Status> v2Mean(List<MachineV2Status> selectedData) {
        List<MachineV2Status> res = new ArrayList<>();
        for (int i = 0; i < selectedData.size() - 1; i++) {
            MachineV2Status current = selectedData.get(i);
            MachineV2Status next    = selectedData.get(i + 1);

            long interval = next.getCreateAt() - current.getCreateAt();
            //计算两个时间点之前的时间间隔中可以插入多少数据
            int missingCount = (int) Math.ceil(interval / (v2v3Interval + v2v3Bias)) - 1;
            //计算插入数据的时间点
            int insertInterval = missingCount > 0 ? (int) interval / (missingCount + 1) : 0;
            long createTime = current.getCreateAt() + insertInterval;
            for (int j = 0; j < missingCount; j++) {
                createTime = createTime + j * insertInterval;

                //需要手动调节的属性和blockFlag都不取平均值，直接取前一个数据的值
                MachineV2Status createdData = new MachineV2Status(
                        current.getUid(),
                        (current.getPm2_5() + next.getPm2_5()) / 2,
                        (current.getTemp() + next.getTemp()) / 2,
                        (current.getHumid() + next.getHumid()) / 2,
                        (current.getCo2() + next.getCo2()) / 2,
                        (current.getVolume() + next.getVolume()) / 2,
                        current.getPower(),
                        current.getMode(),
                        current.getHeat(),
                        current.getLight(),
                        current.getLock(),
                        current.isBlockFlag(),
                        createTime,
                        CompleteMethodEnum.MEAN.getCode()
                );
                res.add(createdData);
            }
        }
        return res;
    }

    @Override
    public List<MachineV3Status> v3Mean(List<MachineV3Status> selectedData) {
        List<MachineV3Status> res = new ArrayList<>();
        for (int i = 0; i < selectedData.size() - 1; i++) {
            MachineV3Status current = selectedData.get(i);
            MachineV3Status next    = selectedData.get(i + 1);

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
                        (current.getPm2_5a() + next.getPm2_5a()) / 2,
                        (current.getPm2_5b() + next.getPm2_5b()) / 2,
                        (current.getTempIndoor() + next.getTempIndoor()) / 2,
                        (current.getTempOutdoor() + next.getTempOutdoor()) / 2,
                        (current.getHumidity() + next.getHumidity()) / 2,
                        (current.getCo2() + next.getCo2()) / 2,
                        current.getStatus(),
                        current.getMode(),
                        (current.getVolume() + next.getVolume() / 2),
                        current.getHeat(),
                        current.getLight(),
                        current.getChildlock(),
                        createTime,
                        CompleteMethodEnum.MEAN.getCode()
                );
                res.add(createdData);
            }
        }
        return res;
    }
}
