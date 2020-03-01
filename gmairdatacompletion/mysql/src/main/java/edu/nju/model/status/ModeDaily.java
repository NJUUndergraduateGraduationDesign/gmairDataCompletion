package edu.nju.model.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 23:10
 * @description：
 */

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeDaily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uid;

    private int autoMinute;
    private int manualMinute;
    private int sleepMinute;

    private long createAt;
    private Integer completeMethod;
}

