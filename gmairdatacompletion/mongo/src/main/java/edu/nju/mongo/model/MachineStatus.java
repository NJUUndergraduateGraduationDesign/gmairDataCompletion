package edu.nju.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:15
 * @description：copy from gmair.model.machine
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("machine_status")
public class MachineStatus {
    private boolean blockFlag;

    private long createAt;

    private String uid;

    /*length = 2*/
    private int pm2_5;

    /*length = 1*/
    private int temp;

    /*length = 1*/
    private int humid;

    /*length = 2*/
    private int co2;

    /*length = 2*/
    private int volume;

    /*length = 1*/
    private int power;

    /*length = 1*/
    private int mode;

    /*length = 1*/
    private int heat;

    /*light = 1*/
    private int light;

    private int lock;
}
