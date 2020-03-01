package edu.nju.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import edn.nju.enums.CompleteMethodEnum;
import edn.nju.util.TimeUtil;
import edu.nju.dao.*;
import edu.nju.model.MachineV2Status;
import edu.nju.model.status.*;
import edu.nju.service.MachinePartialStatusService;
import edu.nju.service.MachineStatusService;
import edu.nju.service.MachineV2StatusService;
import edu.nju.service.MachineV3StatusService;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 23:27
 * @description：implement of machine status service
 */

@Service
public class MachineStatusServiceImpl implements MachineStatusService {
    @Resource
    MachineV2StatusService machineV2StatusServiceImpl;
    @Resource
    MachineV3StatusService machineV3StatusServiceImpl;
    @Resource
    MachinePartialStatusService machinePartialStatusServiceImpl;
    @Resource
    Co2HourlyDao co2HourlyDaoImpl;
    @Resource
    HeatHourlyDao heatHourlyDaoImpl;
    @Resource
    HumidHourlyDao humidHourlyDaoImpl;
    @Resource
    IndoorPm25HourlyDao indoorPm25HourlyDaoImpl;
    @Resource
    InnerPm25HourlyDao innerPm25HourlyDaoImpl;
    @Resource
    ModeHourlyDao modeHourlyDaoImpl;
    @Resource
    PowerHourlyDao powerHourlyDaoImpl;
    @Resource
    TempHourlyDao tempHourlyDaoImpl;
    @Resource
    VolumeHourlyDao volumeHourlyDaoImpl;


    private static final List<Integer> COMPLETE_METHOD_CODE_LIST = CompleteMethodEnum.getAllCompleteMethodCode();

    /*
    目前使用的v2、v3均为一分钟两次数据
     */
    private static final int PACKET_PER_MINUTE = 2;

    /*
    分v2,partial,v3;
    得到所有uid并遍历;
    按小时得到数据;
    转换数据结构,入库；
     */
    @Override
    public void handleAllData() {
        handlePartialData();
        handleV2Data();
        handleV3Data();
    }

    private void handleV2Data() {
        //得到所有v2的uid
        List<String> uidList = machineV2StatusServiceImpl.getAllUids();
        //按uid遍历
        for (String uid : uidList) {
            //当前uid以小时为单位的统计数据
            List<MachineStatusHourly> hourlyList = Lists.newArrayList();
            //得到当前uid开始的记录时间和结束的记录时间
            long time = machineV2StatusServiceImpl.getStartTimeByUid(uid);
            long startOfThisHour = TimeUtil.startOfThisHour(time);
            long startOfNextHour = TimeUtil.startOfNextHour(time);
            List<MachineV2Status> machineV2StatusList;
            //对当前uid按小时获取记录,直到最后一个小时
            while (!(machineV2StatusList =
                    machineV2StatusServiceImpl.fetchBatchByUid(uid, startOfThisHour, startOfNextHour))
                    .isEmpty()) {                //当前uid,当前小时
                //去除无效数据
                machineV2StatusList.removeIf(e -> !e.isBlockFlag());
                //筛选出原始数据
                List<MachineV2Status> originalDataList = machineV2StatusList.stream()
                        .filter(e -> e.getCompleteCode() == CompleteMethodEnum.NONE.getCode())
                        .collect(Collectors.toList());
                //如果当前小时没有原始数据也会存入一条全为0的记录
                if (originalDataList.isEmpty()) {
                    hourlyList.add(new MachineStatusHourly(uid, CompleteMethodEnum.NONE.getCode(), startOfThisHour,
                            0, 0, 0, 0,
                            0, 0, 0, 0,
                            0, 0, 0));
                } else {
                    MachineStatusHourly machineStatusHourly = toMachineStatusHourly(originalDataList, CompleteMethodEnum.NONE.getCode(), startOfThisHour);
                    hourlyList.add(machineStatusHourly);
                }
                for (Integer code : COMPLETE_METHOD_CODE_LIST) {
                    List<MachineV2Status> completeDataList = machineV2StatusList.stream()
                            .filter(e -> e.getCompleteCode() == code)
                            .collect(Collectors.toList());
                    //如果当前小时没有补全方法生成的数据,则不存
                    if (completeDataList.isEmpty()) {
                        continue;
                    }
                    completeDataList.addAll(originalDataList);
                    MachineStatusHourly machineStatusHourly = toMachineStatusHourly(completeDataList, code, startOfThisHour);
                    hourlyList.add(machineStatusHourly);
                }
            }
            //一个uid批量保存一次;
            saveMachineStatusHourlyList(hourlyList);
        }
    }

    private void handleV3Data() {

    }

    private void handlePartialData() {

    }

    /*
    对v2同一个uid,一个小时,某种补全方法的数据统计
    list不为空
     */
    private MachineStatusHourly toMachineStatusHourly(List<MachineV2Status> list, int completeCode, long createAt) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(list));

        int workingCount = (int) list.stream().filter(e -> e.getPower() == 1).count();
        double averagePm25 = workingCount > 0 ? list.stream().filter(e -> e.getPower() == 1).mapToDouble(MachineV2Status::getPm2_5).average().getAsDouble() : 0;
        double averageVolume = workingCount > 0 ? list.stream().mapToDouble(MachineV2Status::getVolume).average().getAsDouble() : 0;
        double averageHumid = list.stream().mapToDouble(MachineV2Status::getHumid).average().getAsDouble();
        double averageTemp = list.stream().mapToDouble(MachineV2Status::getTemp).average().getAsDouble();
        int powerOffMinute = (int) list.stream().filter(a -> a.getPower() == 0).count() / PACKET_PER_MINUTE;
        int powerOnMinute = (int) list.stream().filter(a -> a.getPower() == 1).count() / PACKET_PER_MINUTE;
        int autoMinute = workingCount > 0 ? (int) list.stream().filter(e -> e.getPower() == 1).filter(e -> e.getMode() == 0).count() / PACKET_PER_MINUTE : 0;
        int manualMinute = workingCount > 0 ? (int) list.stream().filter(e -> e.getPower() == 1).filter(e -> e.getMode() == 1).count() / PACKET_PER_MINUTE : 0;
        int sleepMinute = workingCount > 0 ? (int) list.stream().filter(e -> e.getPower() == 1).filter(e -> e.getMode() == 2).count() / PACKET_PER_MINUTE : 0;
        int heatOffMinute = (int) list.stream().filter(a -> a.getHeat() == 0).count() / PACKET_PER_MINUTE;
        int heatOnMinute = (int) list.stream().filter(a -> a.getHeat() == 1).count() / PACKET_PER_MINUTE;
        String uid = list.get(0).getUid();
        return MachineStatusHourly.builder().uid(uid).completeCode(completeCode).averagePm25(averagePm25)
                .averageVolume(averageVolume).averageHumid(averageHumid).averageTemp(averageTemp)
                .powerOffMinute(powerOffMinute).powerOnMinute(powerOnMinute).autoMinute(autoMinute)
                .manualMinute(manualMinute).sleepMinute(sleepMinute).heatOffMinute(heatOffMinute)
                .heatOnMinute(heatOnMinute).createAt(createAt).build();

    }

    private void saveMachineStatusHourlyList(List<MachineStatusHourly> list) {
        for (MachineStatusHourly status : list) {
            saveMachineStatusHourly(status);
        }
    }

    private void saveMachineStatusHourly(MachineStatusHourly status) {
        Co2Hourly co2Hourly = new Co2Hourly();
        BeanUtils.copyProperties(status, co2Hourly);
        co2HourlyDaoImpl.add(co2Hourly);

        HeatHourly heatHourly = new HeatHourly();
        BeanUtils.copyProperties(status, heatHourly);
        heatHourlyDaoImpl.add(heatHourly);

        HumidHourly humidHourly = new HumidHourly();
        BeanUtils.copyProperties(status, humidHourly);
        humidHourlyDaoImpl.add(humidHourly);

        IndoorPm25Hourly indoorPm25Hourly = new IndoorPm25Hourly();
        BeanUtils.copyProperties(status, indoorPm25Hourly);
        indoorPm25Hourly.setAveragePm25(status.getAveragePm25());
        indoorPm25HourlyDaoImpl.add(indoorPm25Hourly);

        ModeHourly modeHourly = new ModeHourly();
        BeanUtils.copyProperties(status, modeHourly);
        modeHourlyDaoImpl.add(modeHourly);

        PowerHourly powerHourly = new PowerHourly();
        BeanUtils.copyProperties(status, powerHourly);
        powerHourlyDaoImpl.add(powerHourly);

        TempHourly tempHourly = new TempHourly();
        BeanUtils.copyProperties(status, tempHourly);
        tempHourlyDaoImpl.add(tempHourly);

        VolumeHourly volumeHourly = new VolumeHourly();
        BeanUtils.copyProperties(status, volumeHourly);
        volumeHourlyDaoImpl.add(volumeHourly);
    }

    @Data
    @Builder
    private static class MachineStatusHourly {
        private String uid;
        private int completeCode;
        private long createAt;
        // 这里的是pm2.5a(indoorPm25)
        // pm2.5b(innerPm25)由partial单独统计存储
        private double averagePm25;
        private double averageVolume;
        private double averageHumid;
        private double averageTemp;
        private int powerOffMinute;
        private int powerOnMinute;
        private int autoMinute;
        private int manualMinute;
        private int sleepMinute;
        private int heatOffMinute;
        private int heatOnMinute;
    }
}
