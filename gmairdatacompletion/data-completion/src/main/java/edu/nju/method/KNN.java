package edu.nju.method;

import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/28 21:11
 * @description: 具体补全方法--K近邻法，所有方法返回值均为缺失数据。
 */
public interface KNN {
    List<MachinePartialStatus> partialKNN(List<MachinePartialStatus> selectedData);

    List<MachineV2Status> v2KNN(List<MachineV2Status> selectedData);

    List<MachineV3Status> v3KNN(List<MachineV3Status> selectedData);
}
