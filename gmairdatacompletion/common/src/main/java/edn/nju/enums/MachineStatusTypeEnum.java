package edn.nju.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 14:26
 * @description：enum of machineStatus:partial,v1,v2 and v3
 */

@Getter
@AllArgsConstructor
public enum MachineStatusTypeEnum {
    MACHINE_PARTIAL_STATUS(0, "partial"),
    MACHINE_V1_STATUS(1, "v1"),
    MACHINE_V2_STATUS(2, "v2"),
    MACHINE_V3_STATUS(3, "v3");

    private int code;
    private String name;
}
