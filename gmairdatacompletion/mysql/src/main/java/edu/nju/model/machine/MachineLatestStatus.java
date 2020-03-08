package edu.nju.model.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 12:28
 * @description: TODO
 */

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineLatestStatus {

    @Id
    private String uid;

    private int power;

    private int heat;

    private int mode;

    private int overCount;
}
