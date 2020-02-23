package edu.nju.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 15:15
 * @description：copy from gmair.model.machine
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("machine_v2_status")
public class MachineV2Status {
    private String uid;

    private int pm2_5;

    private int temp;

    private int humid;

    private int co2;

    private int volume;

    private int power;

    private int mode;

    private int heat;

    private int light;

    private int lock;

    private boolean blockFlag;

    private long createAt;
}
