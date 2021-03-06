package edu.nju.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import edn.nju.enums.CompleteMethodEnum;
import edn.nju.util.TimeUtil;
import edu.nju.bo.MachineV2StatusDaily;
import edu.nju.bo.MachineV2StatusHourly;
import edu.nju.bo.MachineV3StatusDaily;
import edu.nju.bo.MachineV3StatusHourly;
import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;
import edu.nju.model.status.InnerPm25Daily;
import edu.nju.model.status.InnerPm25Hourly;
import edu.nju.service.MachinePartialStatusService;
import edu.nju.service.MachineStatusAnalyzeService;
import edu.nju.service.MachineV2StatusService;
import edu.nju.service.MachineV3StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.CompositeIterator;

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
    @Resource
    MachinePartialStatusService machinePartialStatusServiceImpl;

    private static final List<Integer> ALL_CODE_LIST = CompleteMethodEnum.getAllCode();

    //private static final List<Integer> ALL_CODE_LIST = Lists.newArrayList(CompleteMethodEnum.NONE.getCode());

    //60分钟取一次数据
    private static final long TIME_INTERVAL = 60 * 60 * 1000L;
    //时间区间为左右各30s
    private static final long TIME_BIAS = 30 * 1000L;

    @Override
    public List<MachineV2StatusHourly> analyzeV2(String uid, long startTime) {
        //当前uid,当前小时
        List<MachineV2StatusHourly> hourlyList = Lists.newArrayList();
        long endTime = TimeUtil.startOfNextHour(startTime);
        List<MachineV2Status> machineV2StatusList =
                machineV2StatusServiceImpl.fetchBatchByUid(uid, startTime, endTime, TIME_INTERVAL, TIME_BIAS);
        log.debug("fetchSize:{}", machineV2StatusList.size());
        //去除无效数据
        machineV2StatusList.removeIf(MachineV2Status::isBlockFlag);

        for (Integer code : ALL_CODE_LIST) {
            List<MachineV2Status> completeDataList = machineV2StatusList.stream()
                    .filter(e -> e.getCompleteCode() == code || e.getCompleteCode() == CompleteMethodEnum.NONE.getCode())
                    .collect(Collectors.toList());
            if (completeDataList.isEmpty()) {
                hourlyList.add(new MachineV2StatusHourly(uid, code, startTime));
            } else {
                MachineV2StatusHourly machineV2StatusHourly = toMachineV2StatusHourly(completeDataList, code, startTime);
                hourlyList.add(machineV2StatusHourly);
                log.debug("completeMethod:{},completeDataListSize:{},completeHourly:{}", code, completeDataList.size(), machineV2StatusHourly);
            }
        }
        return hourlyList;
    }

    @Override
    public List<InnerPm25Hourly> analyzePartial(String uid, long startTime) {
        //当前uid,当前小时
        List<InnerPm25Hourly> hourlyList = Lists.newArrayList();
        long endTime = TimeUtil.startOfNextHour(startTime);
        List<MachinePartialStatus> machinePartialStatusList =
                machinePartialStatusServiceImpl.fetchBatchByUid(uid, startTime, endTime);
        log.debug("fetchSize:{}", machinePartialStatusList.size());
        //去除无效数据
        machinePartialStatusList.removeIf(MachinePartialStatus::isBlockFlag);

        for (Integer code : ALL_CODE_LIST) {
            List<MachinePartialStatus> completeDataList = machinePartialStatusList.stream()
                    .filter(e -> e.getCompleteCode() == code || e.getCompleteCode() == CompleteMethodEnum.NONE.getCode())
                    .collect(Collectors.toList());
            if (completeDataList.isEmpty()) {
                hourlyList.add(new InnerPm25Hourly(uid, startTime, code, 0.0));
            } else {
                InnerPm25Hourly machinePartialStatusHourly = toInnerPm25Hourly(completeDataList, code, startTime);
                hourlyList.add(machinePartialStatusHourly);
                log.debug("completeMethod:{},completeDataListSize:{},completeHourly:{}", code, completeDataList.size(), machinePartialStatusHourly);
            }
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
        log.debug("fetchSize:{}", machineV3StatusList.size());
        //去除无效数据
        machineV3StatusList.removeIf(MachineV3Status::isBlockFlag);

        for (Integer code : ALL_CODE_LIST) {
            List<MachineV3Status> completeDataList = machineV3StatusList.stream()
                    .filter(e -> e.getCompleteCode() == code || e.getCompleteCode() == CompleteMethodEnum.NONE.getCode())
                    .collect(Collectors.toList());
            if (completeDataList.isEmpty()) {
                hourlyList.add(new MachineV3StatusHourly(uid, code, startTime));
            } else {
                MachineV3StatusHourly machineV3StatusHourly = toMachineV3StatusHourly(completeDataList, code, startTime);
                hourlyList.add(machineV3StatusHourly);
                log.debug("completeMethod:{},completeDataListSize:{},completeHourly:{}", code, completeDataList.size(), machineV3StatusHourly);
            }
        }
        return hourlyList;
    }

    @Override
    public List<MachineV2StatusDaily> v2HourlyToDaily(List<MachineV2StatusHourly> hourlyList, long startTime) {
        List<MachineV2StatusDaily> res = Lists.newArrayList();
        log.debug("startTime:{},hourlyListSize:{}", startTime, hourlyList.size());
        for (Integer code : ALL_CODE_LIST) {
            List<MachineV2StatusHourly> completeDataList = hourlyList.stream()
                    .filter(e -> e.getCompleteMethod() == code)
                    .collect(Collectors.toList());
            //如果没有hourly数据，抛出异常
            Preconditions.checkArgument(!completeDataList.isEmpty());
            MachineV2StatusDaily machineV2StatusDaily = toMachineV2StatusDaily(completeDataList, code, startTime);
            log.debug("completeMethod:{},completeDataListSize:{},completeDaily:{}", code, completeDataList.size(), machineV2StatusDaily);
            res.add(machineV2StatusDaily);
        }
        return res;
    }

    @Override
    public List<MachineV3StatusDaily> v3HourlyToDaily(List<MachineV3StatusHourly> hourlyList, long startTime) {
        List<MachineV3StatusDaily> res = Lists.newArrayList();
        for (Integer code : ALL_CODE_LIST) {
            List<MachineV3StatusHourly> completeDataList = hourlyList.stream()
                    .filter(e -> e.getCompleteMethod() == code)
                    .collect(Collectors.toList());
            //如果没有hourly数据，抛出异常
            Preconditions.checkArgument(!completeDataList.isEmpty());
            MachineV3StatusDaily machineV3StatusDaily = toMachineV3StatusDaily(completeDataList, code, startTime);
            log.debug("completeMethod:{},completeDataListSize:{},completeDaily:{}", code, completeDataList.size(), machineV3StatusDaily);
            res.add(machineV3StatusDaily);
        }
        return res;
    }

    @Override
    public List<InnerPm25Daily> partialHourlyToDaily(List<InnerPm25Hourly> hourlyList, long startTime) {
        List<InnerPm25Daily> res = Lists.newArrayList();
        for (Integer code : ALL_CODE_LIST) {
            List<InnerPm25Hourly> completeDataList = hourlyList.stream()
                    .filter(e -> e.getCompleteMethod() == code)
                    .collect(Collectors.toList());
            //如果没有hourly数据，抛出异常
            Preconditions.checkArgument(!completeDataList.isEmpty());
            InnerPm25Daily innerPm25Daily = toInnerPM25Daily(completeDataList, code, startTime);
            log.debug("completeMethod:{},completeDataListSize:{},completeDaily:{}", code, completeDataList.size(), innerPm25Daily);
            res.add(innerPm25Daily);
        }
        return res;
    }

    /*
     对v2同一个uid,一个小时,某种补全方法的数据统计
     list不为空
    */
    private MachineV2StatusHourly toMachineV2StatusHourly(List<MachineV2Status> list, int completeCode, long createAt) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(list));

        double averageCo2 = list.stream().mapToDouble(MachineV2Status::getCo2).average().getAsDouble();
        double averagePm25 = list.stream().mapToDouble(MachineV2Status::getPm2_5).average().getAsDouble();
        double averageVolume = list.stream().mapToDouble(MachineV2Status::getVolume).average().getAsDouble();
        double averageHumid = list.stream().mapToDouble(MachineV2Status::getHumid).average().getAsDouble();
        double averageTemp = list.stream().mapToDouble(MachineV2Status::getTemp).average().getAsDouble();

        long before = createAt;
        long end = TimeUtil.startOfNextHour(createAt);
        //在整点补最后一个用于统计
        MachineV2Status last = new MachineV2Status();
        BeanUtils.copyProperties(list.get(list.size() - 1), last);
        last.setCreateAt(end);
        list.add(last);
        long powerOffTime = 0, powerOnTime = 0, autoTime = 0, manualTime = 0, sleepTime = 0, heatOffTime = 0, heatOnTime = 0;
        for (MachineV2Status status : list) {
            long cur = status.getCreateAt();
            if (status.getHeat() == 1) {
                heatOnTime += cur - before;
            } else if (status.getHeat() == 0) {
                heatOffTime += cur - before;
            }
            if (status.getPower() == 1) {
                powerOnTime += cur - before;
                if (status.getMode() == 0) {
                    autoTime += cur - before;
                } else if (status.getMode() == 1) {
                    manualTime += cur - before;
                } else if (status.getMode() == 2) {
                    sleepTime += cur - before;
                }
            } else if (status.getPower() == 0) {
                powerOffTime += cur - before;
            }
            before = status.getCreateAt();
        }
        int heatOnMinute = TimeUtil.millisecondsToMinute(heatOnTime);
        int heatOffMinute = TimeUtil.millisecondsToMinute(heatOffTime);
        int powerOnMinute = TimeUtil.millisecondsToMinute(powerOnTime);
        int powerOffMinute = TimeUtil.millisecondsToMinute(powerOffTime);
        int autoMinute = TimeUtil.millisecondsToMinute(autoTime);
        int manualMinute = TimeUtil.millisecondsToMinute(manualTime);
        int sleepMinute = TimeUtil.millisecondsToMinute(sleepTime);

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
     对v3同一个uid,一个小时,某种补全方法的数据统计
     list不为空
    */
    private MachineV3StatusHourly toMachineV3StatusHourly(List<MachineV3Status> list, int completeCode, long createAt) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(list));

        double averageCo2 = list.stream().mapToDouble(MachineV3Status::getCo2).average().getAsDouble();
        double averageInnerPm25 = list.stream().mapToDouble(MachineV3Status::getPm2_5b).average().getAsDouble();
        double averageIndoorPm25 = list.stream().mapToDouble(MachineV3Status::getPm2_5a).average().getAsDouble();
        double averageVolume = list.stream().mapToDouble(MachineV3Status::getVolume).average().getAsDouble();
        double averageHumid = list.stream().mapToDouble(MachineV3Status::getHumidity).average().getAsDouble();
        double averageTemp = list.stream().mapToDouble(MachineV3Status::getTempIndoor).average().getAsDouble();

        long before = createAt;
        long end = TimeUtil.startOfNextHour(createAt);
        //在整点补最后一个用于统计
        MachineV3Status last = new MachineV3Status();
        BeanUtils.copyProperties(list.get(list.size() - 1), last);
        last.setCreateAt(end);
        list.add(last);
        long autoTime = 0, manualTime = 0, sleepTime = 0, heatOffTime = 0, heatOnTime = 0;
        for (MachineV3Status status : list) {
            long cur = status.getCreateAt();
            if (status.getHeat() == 1) {
                heatOnTime += cur - before;
            } else if (status.getHeat() == 0) {
                heatOffTime += cur - before;
            }
            if (status.getMode() == 0) {
                autoTime += cur - before;
            } else if (status.getMode() == 1) {
                manualTime += cur - before;
            } else if (status.getMode() == 2) {
                sleepTime += cur - before;
            }
            before = status.getCreateAt();
        }

        int heatOnMinute = TimeUtil.millisecondsToMinute(heatOnTime);
        int heatOffMinute = TimeUtil.millisecondsToMinute(heatOffTime);
        int autoMinute = TimeUtil.millisecondsToMinute(autoTime);
        int manualMinute = TimeUtil.millisecondsToMinute(manualTime);
        int sleepMinute = TimeUtil.millisecondsToMinute(sleepTime);
        int powerOnMinute = 60;
        int powerOffMinute = 0;

        String uid = list.get(0).getUid();
        return MachineV3StatusHourly.builder().uid(uid).completeMethod(completeCode).createAt(createAt)
                .averageVolume(averageVolume).averageHumid(averageHumid).averageTemp(averageTemp)
                .averageIndoorPm25(averageIndoorPm25).averageInnerPm25(averageInnerPm25)
                .powerOffMinute(powerOffMinute).powerOnMinute(powerOnMinute).autoMinute(autoMinute)
                .manualMinute(manualMinute).sleepMinute(sleepMinute).heatOffMinute(heatOffMinute)
                .heatOnMinute(heatOnMinute).averageCo2(averageCo2).build();

    }

    /*
     对v3同一个uid,一天,某种补全方法的数据统计
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

    private InnerPm25Hourly toInnerPm25Hourly(List<MachinePartialStatus> list, int completeCode, long createAt) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(list));

        String uid = list.get(0).getUid();
        double innerPm25 = list.stream().mapToDouble(MachinePartialStatus::getData).average().getAsDouble();
        return new InnerPm25Hourly(uid, createAt, completeCode, innerPm25);
    }

    private InnerPm25Daily toInnerPM25Daily(List<InnerPm25Hourly> completeDataList, Integer code, long startTime) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(completeDataList));

        String uid = completeDataList.get(0).getUid();
        double innerPm25 = completeDataList.stream().mapToDouble(InnerPm25Hourly::getAveragePm25).average().getAsDouble();
        return new InnerPm25Daily(uid, startTime, code, innerPm25);
    }
}
