package edu.nju.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 20:02
 * @description: TODO
 */

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Serializable {

    /**
     * 地级市(区)编码
     */
    @Id
    private String cityId;

    /**
     * 地级市(区)名称
     */
    private String cityName;

    /**
     * 地级市(区)所在省/直辖市名称
     */
    private String provinceName;

}
