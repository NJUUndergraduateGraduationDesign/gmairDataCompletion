package edu.nju.model.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 22:55
 * @description：
 */

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatHourly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uid;

    private int heatOnMinute;
    private int heatOffMinute;

    private long createAt;
    private Integer completeMethod;
}
