package edu.nju.method;

import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;

import java.util.List;

/**
 * 具体补全方法--平均值法
 * 所有方法返回值均为缺失数据
 */
public interface Mean {

    List<MachinePartialStatus> partialMean(List<MachinePartialStatus> selectedData);

    List<MachineV2Status> v2Mean(List<MachineV2Status> selectedData);

    List<MachineV3Status> v3Mean(List<MachineV3Status> selectedData);
}
