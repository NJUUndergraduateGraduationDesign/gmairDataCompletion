package edu.nju.method;

import edn.nju.enums.MachineStatusTypeEnum;

import java.util.List;

public interface CommonOp {

    /**
     * 检查两个时间点之间有没有缺失数据
     * @param currentTime 当前数据的时间点
     * @param nextTime 下一个数据的时间点
     * @param dataType 数据类型
     * @return 返回两个数字：缺失数据数量、缺失数据之间的时间间隔。
     */
    List<Integer> checkMissingData(long currentTime, long nextTime, MachineStatusTypeEnum dataType);

}
