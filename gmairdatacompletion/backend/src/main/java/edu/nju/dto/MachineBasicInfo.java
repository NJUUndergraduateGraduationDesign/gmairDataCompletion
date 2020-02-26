package edu.nju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/26 14:45
 * @description：basicInfo of machine, dto Object
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineBasicInfo {
    private String uid;
    private String codeValue;
    private int isPower;
    private int mode;
    private Date bindTime;
    /*
    过去一个月pm2.5超过25的天数
     */
    private int overCount;
}
