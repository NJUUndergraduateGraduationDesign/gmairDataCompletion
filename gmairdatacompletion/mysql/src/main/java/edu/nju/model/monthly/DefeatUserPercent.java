package edu.nju.model.monthly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/8 15:55
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefeatUserPercent {
    private double pm25Average;
    private double defeatUserPercent;
}
