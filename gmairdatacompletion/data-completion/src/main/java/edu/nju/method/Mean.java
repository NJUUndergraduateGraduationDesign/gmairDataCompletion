package edu.nju.method;

import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV1Status;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;

import java.util.List;

/**
 * 此接口中的所有方法的返回值都是补全的缺失值集合
 */
public interface Mean {

    List<MachinePartialStatus> partialMean(List<MachinePartialStatus> selectedData);

    List<MachineV1Status> v1Mean(List<MachineV1Status> selectedData);

    List<MachineV2Status> v2Mean(List<MachineV2Status> selectedData);

    List<MachineV3Status> v3Mean(List<MachineV3Status> selectedData);
}
