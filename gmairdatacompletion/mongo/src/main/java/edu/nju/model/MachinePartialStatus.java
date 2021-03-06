package edu.nju.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 16:55
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("machine_partial_status")
public class MachinePartialStatus implements MachineCommonStatus{
    private String uid;

    private String name;

    private int data;

    private boolean blockFlag;

    private long createAt;

    private int completeCode;
}
