package edu.nju.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 16:54
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("machine_v3_status")
public class MachineV3Status implements MachineCommonStatus{
    private String uid;

    private int pm2_5a;

    private int pm2_5b;

    private int tempIndoor;

    private int tempOutdoor;

    private int humidity;

    private int co2;

    private int status;

    private int mode;

    private int volume;

    private int heat;

    private int light;

    private int childlock;

    private long createAt;

    private int completeCode;

    @Override
    public boolean isBlockFlag() {
        return false;
    }
}
