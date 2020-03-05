package edu.nju.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import edn.nju.enums.CompleteMethodEnum;
import edn.nju.util.TimeUtil;
import edu.nju.bo.MachineV2StatusDaily;
import edu.nju.bo.MachineV2StatusHourly;
import edu.nju.bo.MachineV3StatusDaily;
import edu.nju.bo.MachineV3StatusHourly;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;
import edu.nju.model.status.InnerPm25Daily;
import edu.nju.model.status.InnerPm25Hourly;
import edu.nju.service.MachineStatusAnalyzeService;
import edu.nju.service.MachineV2StatusService;
import edu.nju.service.MachineV3StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 14:05
 * @description：
 */

@Slf4j
@Service
public class MachineStatusAnalyzeServiceImpl implements MachineStatusAnalyzeService {
    @Resource
    MachineV2StatusService machineV2StatusServiceImpl;
    @Resource
    MachineV3StatusService machineV3StatusServiceImpl;

    private static final List<Integer> COMPLETE_METHOD_CODE_LIST = CompleteMethodEnum.getAllCompleteMethodCode();

    private static final List<Integer> ALL_CODE_LIST = CompleteMethodEnum.getAllCode();

    /*
    目前使用的v2、v3均为一分钟两次数据
     */
    private static final int PACKET_PER_MINUTE = 2;

    @Override
    public List<MachineV2StatusHourly> analyzeV2(String uid, long startTime) {
        //当前uid,当前小时
        List<MachineV2StatusHourly> hourlyList = Lists.newArrayList();
        long endTime = TimeUtil.startOfNextHour(startTime);
        List<MachineV2Status> machineV2StatusList =
                machineV2StatusServiceImpl.fetchBatchByUid(uid, startTime, endTime);
        log.info("fetchSize:{}", machineV2StatusList.size());
        //去除无效数据
        machineV2StatusList.removeIf(MachineV2Status::isBlockFlag);
        //筛选出原始数据
        List<MachineV2Status> originalDataList = machineV2StatusList.stream()
                .filter(e -> e.getCompleteCode() == CompleteMethodEnum.NONE.getCode())
                .collect(Collectors.toList());
        log.info("originalSize:{}", machineV2StatusList.size());
        //如果当前小时没有原始数据也会存入一条全为0的记录
        if (originalDataList.isEmpty()) {
            hourlyList.add(new MachineV2StatusHourly(uid, CompleteMethodEnum.NONE.getCode(), startTime));
        } else {
            MachineV2StatusHourly machineV2StatusHourly = toMachineV2StatusHourly(originalDataList, CompleteMethodEnum.NONE.getCode(), startTime);
            hourlyList.add(machineV2StatusHourly);
            log.info("originalHourly:{}", machineV2StatusHourly);
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
            MachineV2StatusHourly machineV2StatusHourly = toMachineV2StatusHourly(completeDataList, code, startTime);
            log.info("completeMethod:{},completeSize:{},completeHourly:{}", code, completeDataList.size(), machineV2StatusHourly);
            hourlyList.add(machineV2StatusHourly);
        }
        return hourlyList;
    }

    @Override
    public List<MachineV3StatusHourly> analyzeV3(String uid, long startTime) {
        //当前uid,当前小时
        List<MachineV3StatusHourly> hourlyList = Lists.newArrayList();
        long endTime = TimeUtil.startOfNextHour(startTime);
        List<MachineV3Status> machineV3StatusList =
                machineV3StatusServiceImpl.fetchBatchByUid(uid, startTime, endTime);
        log.info("fetchSize:{}", machineV3StatusList.size());
        //去除无效数据
        machineV3StatusList.removeIf(MachineV3Status::isBlockFlag);
        //筛选出原始数据
        List<MachineV3Status> originalDataList = machineV3StatusList.stream()
                .filter(e -> e.getCompleteCode() == CompleteMethodEnum.NONE.getCode())
                .collect(Collectors.toList());
        log.info("originalSize:{}", machineV3StatusList.size());
        //如果当前小时没有原始数据也会存入一条全为0的记录
        if (originalDataList.isEmpty()) {
            hourlyList.add(new MachineV3StatusHourly(uid, CompleteMethodEnum.NONE.getCode(), startTime));
        } else {
            MachineV3StatusHourly machineV3StatusHourly = toMachineV3StatusHourly(originalDataList, CompleteMethodEnum.NONE.getCode(), startTime);
            hourlyList.add(machineV3StatusHourly);
            log.info("originalHourly:{}", machineV3StatusHourly);
        }
        for (Integer code : COMPLETE_METHOD_CODE_LIST) {
            List<MachineV3Status> completeDataList = machineV3StatusList.stream()
                    .filter(e -> e.getCompleteCode() == code)
                    .collect(Collectors.toList());
            //如果当前小时没有补全方法生成的数据,则不存
            if (completeDataList.isEmpty()) {
                continue;
            }
            completeDataList.addAll(originalDataList);
            MachineV3StatusHourly machineV3StatusHourly = toMachineV3StatusHourly(completeDataList, code, startTime);
            log.info("completeMethod:{},completeSize:{},completeHourly:{}", code, completeDataList.size(), machineV3StatusHourly);
            hourlyList.add(machineV3StatusHourly);
        }
        return hourlyList;
    }

    @Override
    public List<InnerPm25Hourly> analyzePartial(String uid, long startTime) {
        return null;
    }

    @Override
    public List<MachineV2StatusDaily> v2HourlyToDaily(List<MachineV2StatusHourly> hourlyList, long startTime) {
        List<MachineV2StatusDaily> res = Lists.newArrayList();
        for (Integer code : ALL_CODE_LIST) {
            List<MachineV2StatusHourly> completeDataList = hourlyList.stream()
                    .filter(e -> e.getCompleteMethod() == code)
                    .collect(Collectors.toList());
            //如果原始数据没有hourly数据，抛出异常
            Preconditions.checkArgument(!(code == CompleteMethodEnum.NONE.getCode() && completeDataList.isEmpty()));
            //如果当天没有补全方法生成的hourly数据,则不存
            if (completeDataList.isEmpty()) {
                continue;
            }
            MachineV2StatusDaily machineV2StatusDaily = toMachineV2StatusDaily(completeDataList, code, startTime);
            log.info("completeMethod:{},completeDaily:{}", code, machineV2StatusDaily);
            res.add(machineV2StatusDaily);
        }
        return res;
    }

    @Override
    public List<MachineV3StatusDaily> v3HourlyToDaily(List<MachineV3StatusHourly> hourlyList, long startTime) {
        return null;
    }

    @Override
    public List<InnerPm25Daily> partialHourlyToDaily(List<InnerPm25Hourly> hourlyList, long startTime) {
        return null;
    }

    /*
     对v2同一个uid,一个小时,某种补全方法的数据统计
     list不为空
    */
    private MachineV2StatusHourly toMachineV2StatusHourly(List<MachineV2Status> list, int completeCode, long createAt) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(list));

        int workingCount = (int) list.stream().filter(e -> e.getPower() == 1).count();
        double averageCo2 = list.stream().mapToDouble(MachineV2Status::getCo2).average().getAsDouble();
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
        return MachineV2StatusHourly.builder().uid(uid).completeMethod(completeCode).averagePm25(averagePm25)
                .averageVolume(averageVolume).averageHumid(averageHumid).averageTemp(averageTemp)
                .powerOffMinute(powerOffMinute).powerOnMinute(powerOnMinute).autoMinute(autoMinute)
                .manualMinute(manualMinute).sleepMinute(sleepMinute).heatOffMinute(heatOffMinute)
                .heatOnMinute(heatOnMinute).averageCo2(averageCo2).createAt(createAt).build();
    }

    /*
     对v2同一个uid,一天,某种补全方法的数据统计
     list不为空
    */
    private MachineV2StatusDaily toMachineV2StatusDaily(List<MachineV2StatusHourly> list, int completeCode, long createAt) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(list));

        double averageCo2 = list.stream().mapToDouble(MachineV2StatusHourly::getAverageCo2).average().getAsDouble();
        double averagePm25 = list.stream().mapToDouble(MachineV2StatusHourly::getAveragePm25).average().getAsDouble();
        double averageVolume = list.stream().mapToDouble(MachineV2StatusHourly::getAverageVolume).average().getAsDouble();
        double averageHumid = list.stream().mapToDouble(MachineV2StatusHourly::getAverageHumid).average().getAsDouble();
        double averageTemp = list.stream().mapToDouble(MachineV2StatusHourly::getAverageTemp).average().getAsDouble();
        int powerOffMinute = list.stream().mapToInt(MachineV2StatusHourly::getPowerOffMinute).sum();
        int powerOnMinute = list.stream().mapToInt(MachineV2StatusHourly::getPowerOnMinute).sum();
        int autoMinute = list.stream().mapToInt(MachineV2StatusHourly::getAutoMinute).sum();
        int manualMinute = list.stream().mapToInt(MachineV2StatusHourly::getManualMinute).sum();
        int sleepMinute = list.stream().mapToInt(MachineV2StatusHourly::getSleepMinute).sum();
        int heatOffMinute = list.stream().mapToInt(MachineV2StatusHourly::getHeatOffMinute).sum();
        int heatOnMinute = list.stream().mapToInt(MachineV2StatusHourly::getHeatOnMinute).sum();
        String uid = list.get(0).getUid();
        return MachineV2StatusDaily.builder().uid(uid).completeMethod(completeCode).averagePm25(averagePm25)
                .averageVolume(averageVolume).averageHumid(averageHumid).averageTemp(averageTemp)
                .powerOffMinute(powerOffMinute).powerOnMinute(powerOnMinute).autoMinute(autoMinute)
                .manualMinute(manualMinute).sleepMinute(sleepMinute).heatOffMinute(heatOffMinute)
                .heatOnMinute(heatOnMinute).averageCo2(averageCo2).createAt(createAt).build();
    }

    /*
     对v2同一个uid,一个小时,某种补全方法的数据统计
     list不为空
    */
    private MachineV3StatusHourly toMachineV3StatusHourly(List<MachineV3Status> list, int completeCode, long createAt) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(list));

        return null;
    }

    /*
     对v2同一个uid,一天,某种补全方法的数据统计
     list不为空
    */
    private MachineV3StatusDaily toMachineV3StatusDaily(List<MachineV3StatusHourly> list, int completeCode, long createAt) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(list));

        double averageCo2 = list.stream().mapToDouble(MachineV3StatusHourly::getAverageCo2).average().getAsDouble();
        double averageIndoorPm25 = list.stream().mapToDouble(MachineV3StatusHourly::getAverageIndoorPm25).average().getAsDouble();
        double averageInnerPm25 = list.stream().mapToDouble(MachineV3StatusHourly::getAverageInnerPm25).average().getAsDouble();
        double averageVolume = list.stream().mapToDouble(MachineV3StatusHourly::getAverageVolume).average().getAsDouble();
        double averageHumid = list.stream().mapToDouble(MachineV3StatusHourly::getAverageHumid).average().getAsDouble();
        double averageTemp = list.stream().mapToDouble(MachineV3StatusHourly::getAverageTemp).average().getAsDouble();
        int powerOffMinute = list.stream().mapToInt(MachineV3StatusHourly::getPowerOffMinute).sum();
        int powerOnMinute = list.stream().mapToInt(MachineV3StatusHourly::getPowerOnMinute).sum();
        int autoMinute = list.stream().mapToInt(MachineV3StatusHourly::getAutoMinute).sum();
        int manualMinute = list.stream().mapToInt(MachineV3StatusHourly::getManualMinute).sum();
        int sleepMinute = list.stream().mapToInt(MachineV3StatusHourly::getSleepMinute).sum();
        int heatOffMinute = list.stream().mapToInt(MachineV3StatusHourly::getHeatOffMinute).sum();
        int heatOnMinute = list.stream().mapToInt(MachineV3StatusHourly::getHeatOnMinute).sum();
        String uid = list.get(0).getUid();
        return MachineV3StatusDaily.builder().uid(uid).completeMethod(completeCode).createAt(createAt)
                .averageVolume(averageVolume).averageHumid(averageHumid).averageTemp(averageTemp)
                .averageIndoorPm25(averageIndoorPm25).averageInnerPm25(averageInnerPm25)
                .powerOffMinute(powerOffMinute).powerOnMinute(powerOnMinute).autoMinute(autoMinute)
                .manualMinute(manualMinute).sleepMinute(sleepMinute).heatOffMinute(heatOffMinute)
                .heatOnMinute(heatOnMinute).averageCo2(averageCo2).build();
    }
}
