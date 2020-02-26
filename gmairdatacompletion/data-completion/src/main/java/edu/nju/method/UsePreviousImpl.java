package edu.nju.method;

import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV1Status;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;

import java.util.List;

public class UsePreviousImpl implements UsePrevious {
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
        return null;
    }

    @Override
    public List<MachineV3Status> v3UsePrevious(List<MachineV3Status> selectedData) {
        return null;
    }
}
