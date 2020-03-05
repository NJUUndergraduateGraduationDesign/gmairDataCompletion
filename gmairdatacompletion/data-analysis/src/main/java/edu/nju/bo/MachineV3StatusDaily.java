package edu.nju.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 16:29
 * @description：
 */

@Data
@Builder
@AllArgsConstructor
public class MachineV3StatusDaily {
    private String uid;
    private int completeMethod;
    private long createAt;
    private double averageCo2;
    private double averageIndoorPm25;
    private double averageInnerPm25;
    private double averageVolume;
    private double averageHumid;
    private double averageTemp;
    private int powerOffMinute;
    private int powerOnMinute;
    private int autoMinute;
    private int manualMinute;
    private int sleepMinute;
    private int heatOffMinute;
    private int heatOnMinute;

    public MachineV3StatusDaily(String uid, int completeMethod, long createAt) {
        this.uid = uid;
        this.completeMethod = completeMethod;
        this.createAt = createAt;
    }
}
