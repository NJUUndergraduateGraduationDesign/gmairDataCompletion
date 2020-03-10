package edu.nju.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：CK
 * @date ：Created in 2020/3/10 13:03
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryNumber implements Serializable {

    private int type;

    private long number;
}
