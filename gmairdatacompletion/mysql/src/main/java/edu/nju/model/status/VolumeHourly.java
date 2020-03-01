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
public class VolumeHourly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uid;

    private double averageVolume;

    private long createAt;
    private Integer completeMethod;
}