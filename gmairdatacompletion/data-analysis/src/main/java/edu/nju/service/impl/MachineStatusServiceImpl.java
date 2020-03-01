package edu.nju.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import edn.nju.enums.CompleteMethodEnum;
import edn.nju.util.TimeUtil;
import edu.nju.bo.MachineV2StatusHourly;
import edu.nju.model.MachineV2Status;
import edu.nju.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 23:27
 * @description：get machine_xx_status from mongodb and translate to MachineStatusHourly business object
 */

@Slf4j
@Service
public class MachineStatusServiceImpl implements MachineStatusService {
    @Resource
    MachineV2StatusService machineV2StatusServiceImpl;
    @Resource
    MachineStatusHourlyService machineStatusHourlyServiceImpl;


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
        //handleAllPartialData();
        handleAllV2Data();
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

    }

    @Override
    public void handleAllPartialData() {

    }


    @Override
    public void handleV2Data(List<String> uidList) {
        //按uid遍历
        for (String uid : uidList) {
            log.info("uid:{}",uid);

            //当前uid以小时为单位的统计数据
            List<MachineV2StatusHourly> hourlyList = Lists.newArrayList();
            //得到当前uid开始的记录时间和结束的记录时间
            long startTime = machineV2StatusServiceImpl.getStartTimeByUid(uid);
            long start = TimeUtil.startOfThisHour(startTime);
            long endTime = machineV2StatusServiceImpl.getLatestTimeByUid(uid);
            long end = TimeUtil.startOfThisHour(endTime);
            log.info("startTime:{},endTime:{}",start,end);

            List<MachineV2Status> machineV2StatusList;
            //对当前uid按小时获取记录,直到最后一个小时
            for (long cur = start; cur <= end; cur = TimeUtil.startOfNextHour(cur)) {
                //当前uid,当前小时
                long curNextHour = TimeUtil.startOfNextHour(cur);
                machineV2StatusList =
                        machineV2StatusServiceImpl.fetchBatchByUid(uid, cur, curNextHour);
                log.info("fetchSize:{}",machineV2StatusList.size());
                //去除无效数据
                machineV2StatusList.removeIf(MachineV2Status::isBlockFlag);
                //筛选出原始数据
                List<MachineV2Status> originalDataList = machineV2StatusList.stream()
                        .filter(e -> e.getCompleteCode() == CompleteMethodEnum.NONE.getCode())
                        .collect(Collectors.toList());
                log.info("originalSize:{}",machineV2StatusList.size());
                //如果当前小时没有原始数据也会存入一条全为0的记录
                if (originalDataList.isEmpty()) {
                    hourlyList.add(new MachineV2StatusHourly(uid, CompleteMethodEnum.NONE.getCode(), cur,
                            0, 0, 0, 0,
                            0, 0, 0, 0,
                            0, 0, 0));
                } else {
                    MachineV2StatusHourly machineV2StatusHourly = toMachineStatusHourly(originalDataList, CompleteMethodEnum.NONE.getCode(), cur);
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
                    MachineV2StatusHourly machineV2StatusHourly = toMachineStatusHourly(completeDataList, code, cur);
                    log.info("completeMethod:{},completeSize:{},completeHourly:{}",code,completeDataList.size(), machineV2StatusHourly);
                    hourlyList.add(machineV2StatusHourly);
                }
            }
            //一个uid批量保存一次;
            machineStatusHourlyServiceImpl.saveMachineStatusHourlyList(hourlyList);
        }
    }

    @Override
    public void handleV3Data(List<String> uidList) {

    }

    @Override
    public void handlePartialData(List<String> uidList) {

    }

    /*
        对v2同一个uid,一个小时,某种补全方法的数据统计
        list不为空
         */
    private MachineV2StatusHourly toMachineStatusHourly(List<MachineV2Status> list, int completeCode, long createAt) {
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
        return MachineV2StatusHourly.builder().uid(uid).completeMethod(completeCode).averagePm25(averagePm25)
                .averageVolume(averageVolume).averageHumid(averageHumid).averageTemp(averageTemp)
                .powerOffMinute(powerOffMinute).powerOnMinute(powerOnMinute).autoMinute(autoMinute)
                .manualMinute(manualMinute).sleepMinute(sleepMinute).heatOffMinute(heatOffMinute)
                .heatOnMinute(heatOnMinute).createAt(createAt).build();

    }
}
