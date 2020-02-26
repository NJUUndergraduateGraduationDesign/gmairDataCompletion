package edu.nju.method;

import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV1Status;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;

import java.util.List;

public interface UsePrevious {

    List<MachinePartialStatus> partialUsePrevious(List<MachinePartialStatus> selectedData);

    List<MachineV1Status> v1UsePrevious(List<MachineV1Status> selectedData);

    List<MachineV2Status> v2UsePrevious(List<MachineV2Status> selectedData);

    List<MachineV3Status> v3UsePrevious(List<MachineV3Status> selectedData);
}
