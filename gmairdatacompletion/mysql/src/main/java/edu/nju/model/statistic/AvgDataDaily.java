package edu.nju.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/3/5 21:18
 * @description: TODO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvgDataDaily implements Serializable {

    private long date;

    private double avgData;

}
