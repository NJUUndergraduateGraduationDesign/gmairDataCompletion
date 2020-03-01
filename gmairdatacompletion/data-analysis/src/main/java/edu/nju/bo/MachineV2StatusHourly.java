package edu.nju.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 17:10
 * @description：
 */

@Data
@Builder
@AllArgsConstructor
public class MachineV2StatusHourly {
    private String uid;
    private int completeMethod;
    private long createAt;
    // 这里的是pm2.5a(indoorPm25)
    // pm2.5b(innerPm25)由partial单独统计存储
    private double averagePm25;
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
}
