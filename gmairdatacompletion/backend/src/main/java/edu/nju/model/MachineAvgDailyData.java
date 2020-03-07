package edu.nju.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 10:54
 * @description: TODO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineAvgDailyData {

    private String uid;

    private int avgIndoorPm25;

    private int avgInnerPm25;

    private int avgCo2;

}
