package edu.nju.method;

import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV1Status;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeanImpl implements Mean {

    private static final double v1Interval   = 5000.0;
    private static final double v2v3Interval = 30000.0;
    private static final double bias         = 10000.0;

    @Override
    public List<MachinePartialStatus> partialMean(List<MachinePartialStatus> selectedData) {
        return null;
    }

    @Override
    public List<MachineV1Status> v1Mean(List<MachineV1Status> selectedData) {
        return null;
    }

    @Override
    public List<MachineV2Status> v2Mean(List<MachineV2Status> selectedData) {
        List<MachineV2Status> res = new ArrayList<>();
        for (int i = 0; i < selectedData.size() - 1; i++) {
            MachineV2Status current = selectedData.get(i);
            MachineV2Status next    = selectedData.get(i + 1);

            long interval = next.getCreateAt() - current.getCreateAt();
            //计算两个时间点之前的时间间隔中可以插入多少数据
            int missingCount = (int) Math.ceil(interval / (v2v3Interval + bias)) - 1;
            //计算插入数据的时间点
            int insertInterval = missingCount > 0 ? (int) interval / (missingCount + 1) : 0;
            long createTime = current.getCreateAt() + insertInterval;
            //插入数据
            for (int j = 0; j < missingCount; j++) {
                createTime = createTime + j * insertInterval;

                //一般的测量数据取前后数据的平均值，枚举类型/有限个取值的数据取前一个数据的值
                MachineV2Status createdData = new MachineV2Status();
                createdData.setUid(current.getUid());
                createdData.setPm2_5((current.getPm2_5() + next.getPm2_5()) / 2);
                createdData.setTemp((current.getTemp() + next.getTemp()) / 2);
                createdData.setHumid((current.getHumid() + next.getHumid()) / 2);
                createdData.setCo2((current.getCo2() + next.getCo2()) / 2);
                createdData.setVolume((current.getVolume() + next.getVolume()) / 2);
                createdData.setPower(current.getPower());
                createdData.setMode(current.getMode());
                createdData.setHeat(current.getHeat());
                createdData.setLight((current.getLight() + next.getLight()) / 2);
                createdData.setLock(current.getLock());
                createdData.setBlockFlag(current.isBlockFlag());
                createdData.setCreateAt(createTime);

                res.add(createdData);
            }
        }
        return res;
    }

    @Override
    public List<MachineV3Status> v3Mean(List<MachineV3Status> selectedData) {
        return null;
    }

}
