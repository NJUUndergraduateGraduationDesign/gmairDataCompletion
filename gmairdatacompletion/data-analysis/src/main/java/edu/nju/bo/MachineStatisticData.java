package edu.nju.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 15:03
 * @description: TODO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineStatisticData {

    private double indoorPm25;

    private double innerPm25;

    private double co2;

    private double humid;

    private double temp;

    private double volume;
}
