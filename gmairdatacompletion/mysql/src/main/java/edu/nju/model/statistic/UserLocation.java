package edu.nju.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/3/4 13:22
 * @description: TODO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation implements Serializable {

    /**
     * 省份(直辖市等)/地级市(区)名称
     */
    private String name;

    /**
     * 对应的地区的人数
     */
    private long value;

}
