package edu.nju.model.monthly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/8 15:53
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MostUseMode {
    private String mostUseMode;
    private double mostUseModeHoursCount;
}
