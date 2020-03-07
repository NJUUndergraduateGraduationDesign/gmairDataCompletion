package edu.nju.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import edn.nju.util.TimeUtil;
import edu.nju.bo.MachineV2StatusDaily;
import edu.nju.bo.MachineV2StatusHourly;
import edu.nju.bo.MachineV3StatusDaily;
import edu.nju.bo.MachineV3StatusHourly;
import edu.nju.model.status.InnerPm25Daily;
import edu.nju.model.status.InnerPm25Hourly;
import edu.nju.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 23:27
 * @description： get machine_xx_status from mongodb and translate to MachineStatusHourly business object
 */

@Slf4j
@Service
public class MachineStatusHandleServiceImpl implements MachineStatusHandleService {
    @Resource
    MachineV2StatusService machineV2StatusServiceImpl;
    @Resource
    MachineV3StatusService machineV3StatusServiceImpl;
    @Resource
    MachinePartialStatusService machinePartialStatusServiceImpl;
    @Resource
    MachineStatusDBService machineStatusDBServiceImpl;
    @Resource
    MachineStatusAnalyzeService machineStatusAnalyzeService;

    /*
    分v2,partial,v3;
    得到所有uid并遍历;
    按小时得到数据;
    转换数据结构,入库；
     */
    @Override
    public void handleAllData() {
        //handleAllPartialData();
        //handleAllV2Data();
        //handleAllV3Data();
    }

    @Override
    public void handleAllV2Data() {
        //得到所有v2的uid
        List<String> uidList = machineV2StatusServiceImpl.getAllUids();
        handleV2Data(uidList);
    }

    @Override
    public void handleAllV3Data() {
        List<String> uidList = machineV3StatusServiceImpl.getAllUids();
        handleV2Data(uidList);
    }

    @Override
    public void handleAllPartialData() {
        List<String> uidList = machinePartialStatusServiceImpl.getAllUids();
        handleV2Data(uidList);
    }


    @Override
    public void handleV2Data(List<String> uidList) {
        //按uid遍历
        for (String uid : uidList) {
            List<MachineV2StatusHourly> v2HourlyList = handleV2DataHourly(uid);
            List<MachineV2StatusDaily> v2DailyList = handleV2DataDaily(v2HourlyList);
            machineStatusDBServiceImpl.saveMachineV2StatusHourlyList(v2HourlyList);
            machineStatusDBServiceImpl.saveMachineV2StatusDailyList(v2DailyList);
        }
    }


    @Override
    public void handleV3Data(List<String> uidList) {
        for (String uid : uidList) {
            List<MachineV3StatusHourly> v3HourlyList = handleV3DataHourly(uid);
            List<MachineV3StatusDaily> v3DailyList = handleV3DataDaily(v3HourlyList);
            machineStatusDBServiceImpl.saveMachineV3StatusHourlyList(v3HourlyList);
            machineStatusDBServiceImpl.saveMachineV3StatusDailyList(v3DailyList);
        }
    }

    @Override
    public void handlePartialData(List<String> uidList) {
        for (String uid : uidList) {
            List<InnerPm25Hourly> partialHourlyList = handlePartialDataHourly(uid);
            List<InnerPm25Daily> partialDailyList = handlePartialDataDaily(partialHourlyList);
            machineStatusDBServiceImpl.saveMachinePartialStatusHourlyList(partialHourlyList);
            machineStatusDBServiceImpl.saveMachinePartialStatusDailyList(partialDailyList);
        }
    }

    /*
    对同个uid所有原始数据处理,得到hourly数据
     */
    private List<MachineV2StatusHourly> handleV2DataHourly(String uid) {
        log.info("uid:{}", uid);

        //当前uid以小时为单位的统计数据
        List<MachineV2StatusHourly> hourlyList = Lists.newArrayList();
        //得到当前uid开始的记录时间和结束的记录时间
        long startTime = machineV2StatusServiceImpl.getStartTimeByUid(uid);
        long start = TimeUtil.startOfThisHour(startTime);
        long endTime = machineV2StatusServiceImpl.getLatestTimeByUid(uid);
        long end = TimeUtil.startOfThisHour(endTime);
        log.info("startTime:{},endTime:{}", start, end);

        //对当前uid按小时获取记录,直到最后一个小时
        for (long cur = start; cur <= end; cur = TimeUtil.startOfNextHour(cur)) {
            //当前uid,当前小时
            List<MachineV2StatusHourly> subList = machineStatusAnalyzeService.analyzeV2(uid, cur);
            //当前uid,所有小时
            hourlyList.addAll(subList);
        }
        log.info("uid:{},hourlyListSize:{}", uid, hourlyList.size());
        //一个uid批量保存一次;
        return hourlyList;
    }

    /*
    对同个uid的所有hourly统计数据处理,得到daily数据
     */
    private List<MachineV2StatusDaily> handleV2DataDaily(List<MachineV2StatusHourly> v2hourlyList) {
        Preconditions.checkArgument(!v2hourlyList.isEmpty());
        List<MachineV2StatusDaily> res = Lists.newArrayList();
        long startTime = v2hourlyList.get(0).getCreateAt();
        long start = TimeUtil.startOfThisDay(startTime);
        long endTime = v2hourlyList.get(v2hourlyList.size() - 1).getCreateAt();
        long end = TimeUtil.startOfThisDay(endTime);
        for (long cur = start; cur <= end; cur = TimeUtil.startOfNextDay(cur)) {
            long finalCur = cur;
            long finalCur1 = TimeUtil.startOfNextDay(cur);
            //得到一个uid一天的统计数据,包含原始统计数据和补全统计数据
            List<MachineV2StatusHourly> subList = v2hourlyList.stream()
                    .filter(e -> TimeUtil.isBetweenStartAndEnd(e.getCreateAt(), finalCur, finalCur1))
                    .collect(Collectors.toList());
            List<MachineV2StatusDaily> dailyList = machineStatusAnalyzeService.v2HourlyToDaily(subList, cur);
            res.addAll(dailyList);
        }
        log.info("dailyListSize:{}", res.size());
        return res;
    }

    /*
    对同个uid所有原始数据处理,得到hourly数据
     */
    private List<MachineV3StatusHourly> handleV3DataHourly(String uid) {
        log.info("uid:{}", uid);

        //当前uid以小时为单位的统计数据
        List<MachineV3StatusHourly> hourlyList = Lists.newArrayList();
        //得到当前uid开始的记录时间和结束的记录时间
        long startTime = machineV3StatusServiceImpl.getStartTimeByUid(uid);
        long start = TimeUtil.startOfThisHour(startTime);
        long endTime = machineV3StatusServiceImpl.getLatestTimeByUid(uid);
        long end = TimeUtil.startOfThisHour(endTime);
        log.info("startTime:{},endTime:{}", start, end);

        //对当前uid按小时获取记录,直到最后一个小时
        for (long cur = start; cur <= end; cur = TimeUtil.startOfNextHour(cur)) {
            //当前uid,当前小时
            List<MachineV3StatusHourly> subList = machineStatusAnalyzeService.analyzeV3(uid, cur);
            //当前uid,所有小时
            hourlyList.addAll(subList);
        }
        log.info("uid:{},hourlyListSize:{}", uid, hourlyList.size());
        //一个uid批量保存一次;
        return hourlyList;
    }

    /*
    对同个uid的所有hourly统计数据处理,得到daily数据
     */
    private List<MachineV3StatusDaily> handleV3DataDaily(List<MachineV3StatusHourly> v3hourlyList) {
        Preconditions.checkArgument(!v3hourlyList.isEmpty());
        List<MachineV3StatusDaily> res = Lists.newArrayList();
        long startTime = v3hourlyList.get(0).getCreateAt();
        long start = TimeUtil.startOfThisDay(startTime);
        long endTime = v3hourlyList.get(v3hourlyList.size() - 1).getCreateAt();
        long end = TimeUtil.startOfThisDay(endTime);
        for (long cur = start; cur <= end; cur = TimeUtil.startOfNextDay(cur)) {
            long finalCur = cur;
            long finalCur1 = TimeUtil.startOfNextDay(cur);
            //得到一个uid一天的统计数据,包含原始统计数据和补全统计数据
            List<MachineV3StatusHourly> subList = v3hourlyList.stream()
                    .filter(e -> TimeUtil.isBetweenStartAndEnd(e.getCreateAt(), finalCur, finalCur1))
                    .collect(Collectors.toList());
            List<MachineV3StatusDaily> dailyList = machineStatusAnalyzeService.v3HourlyToDaily(subList, cur);
            res.addAll(dailyList);
        }
        log.info("dailyListSize:{}", res.size());
        return res;
    }

    /*
    对同个uid所有原始数据处理,得到hourly数据
     */
    private List<InnerPm25Hourly> handlePartialDataHourly(String uid) {
        log.info("uid:{}", uid);

        //当前uid以小时为单位的统计数据
        List<InnerPm25Hourly> hourlyList = Lists.newArrayList();
        //得到当前uid开始的记录时间和结束的记录时间
        long startTime = machinePartialStatusServiceImpl.getStartTimeByUid(uid);
        long start = TimeUtil.startOfThisHour(startTime);
        long endTime = machinePartialStatusServiceImpl.getLatestTimeByUid(uid);
        long end = TimeUtil.startOfThisHour(endTime);
        log.info("startTime:{},endTime:{}", start, end);

        //对当前uid按小时获取记录,直到最后一个小时
        for (long cur = start; cur <= end; cur = TimeUtil.startOfNextHour(cur)) {
            //当前uid,当前小时
            List<InnerPm25Hourly> subList = machineStatusAnalyzeService.analyzePartial(uid, cur);
            //当前uid,所有小时
            hourlyList.addAll(subList);
        }
        log.info("uid:{},hourlyListSize:{}", uid, hourlyList.size());
        //一个uid批量保存一次;
        return hourlyList;
    }

    /*
    对同个uid的所有hourly统计数据处理,得到daily数据
     */
    private List<InnerPm25Daily> handlePartialDataDaily(List<InnerPm25Hourly> partialHourlyList) {
        Preconditions.checkArgument(!partialHourlyList.isEmpty());

        List<InnerPm25Daily> res = Lists.newArrayList();
        long startTime = partialHourlyList.get(0).getCreateAt();
        long start = TimeUtil.startOfThisDay(startTime);
        long endTime = partialHourlyList.get(partialHourlyList.size() - 1).getCreateAt();
        long end = TimeUtil.startOfThisDay(endTime);
        for (long cur = start; cur <= end; cur = TimeUtil.startOfNextDay(cur)) {
            long finalCur = cur;
            long finalCur1 = TimeUtil.startOfNextDay(cur);
            //得到一个uid一天的统计数据,包含原始统计数据和补全统计数据
            List<InnerPm25Hourly> subList = partialHourlyList.stream()
                    .filter(e -> TimeUtil.isBetweenStartAndEnd(e.getCreateAt(), finalCur, finalCur1))
                    .collect(Collectors.toList());
            List<InnerPm25Daily> dailyList = machineStatusAnalyzeService.partialHourlyToDaily(subList, cur);
            res.addAll(dailyList);
        }
        log.info("dailyListSize:{}", res.size());
        return res;
    }
}
