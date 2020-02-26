package edu.nju.method.impl;

import edn.nju.enums.MachineStatusTypeEnum;
import edu.nju.method.CommonOp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bright Chan
 * @date 2020/2/26 19:13
 */

@Service
public class CommonOpImpl implements CommonOp {

    private static final double v1Interval   = 5000.0;
    private static final double v1Bias       = 2000.0;  //可优化
    private static final double v2v3Interval = 30000.0;
    private static final double v2v3Bias     = 10000.0;

    @Override
    public List<Integer> checkMissingData(long currentTime, long nextTime,
                                          MachineStatusTypeEnum dataType) {
        List<Integer> res = new ArrayList<>();
        long interval = nextTime - currentTime;
        int missingCount = 0;
        //计算两个时间点之前的时间间隔中可以插入多少数据
        if (dataType.equals(MachineStatusTypeEnum.MACHINE_V2_STATUS) ||
            dataType.equals(MachineStatusTypeEnum.MACHINE_V3_STATUS)) {
            missingCount = (int) Math.ceil(interval / (v2v3Interval + v2v3Bias)) - 1;
        }
        else if (dataType.equals(MachineStatusTypeEnum.MACHINE_V1_STATUS)) {
            missingCount = (int) Math.ceil(interval / (v1Interval + v1Bias)) - 1;
        }
        //计算插入数据的时间点
        int insertInterval = missingCount > 0 ? (int) interval / (missingCount + 1) : 0;

        res.add(missingCount);
        res.add(insertInterval);
        return res;
    }
}
