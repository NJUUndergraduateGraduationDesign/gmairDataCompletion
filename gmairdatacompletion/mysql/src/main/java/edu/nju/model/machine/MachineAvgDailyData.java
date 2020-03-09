package edu.nju.model.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 10:54
 * @description: TODO
 */

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineAvgDailyData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String uid;

    private int avgIndoorPm25;

    private int avgInnerPm25;

    private int avgCo2;

}
