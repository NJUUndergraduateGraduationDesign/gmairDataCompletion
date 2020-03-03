package edu.nju.model.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 14:50
 * @description: TODO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineBasicInfo implements Serializable {
    private String uid;
    /**
     * 二维码，从User中获取
     */
    private String codeValue;
    /**
     * 设备所在的城市编码
     */
    private String city;
    /**
     * 设备最近的开关机状态
     */
    private int isPower;
    /**
     * 设备最近的工作模式：0-自动；1-睡眠；2-手动
     */
    private int mode;
    /**
     * 绑定时间，从User中获取
     */
    private Date bindTime;
    /**
     * 过去一个月pm2.5超过25的天数
     */
    private int overCount;
    /**
     * 设备在筛选时间内的辅热选项：0-辅热关；1-600W；2-1200W.(1和2合并为1，即辅热开)
     */
    private int heat;
}
