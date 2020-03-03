package edu.nju.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Bright Chan
 * @date 2020/3/1 11:03
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineQueryCond implements Serializable {
    /**
     * 当前页，必填
     */
    private int curPage;
    /**
     * 页大小，必填
     */
    private int pageSize;
    /**
     * 设备uid，非必填
     */
    private String uid;
    /**
     * 设备的开关机状态，非必填
     */
    private int isPower;
    /**
     * 设备绑定的起始时间，用于筛选出特定时间内绑定的设备
     */
    private Date createTimeGTE;
    /**
     * 设备绑定的结束时间，用于筛选出特定时间内绑定的设备
     */
    private Date createTimeLTE;
    /**
     * 设备过去一个月pm2.5超过25的最小天数
     */
    private int overCountGTE;
    /**
     * 设备过去一个月pm2.5超过25的最大天数
     */
    private int overCountLTE;
}
