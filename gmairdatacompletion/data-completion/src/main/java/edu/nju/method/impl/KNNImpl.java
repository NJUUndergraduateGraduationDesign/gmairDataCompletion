package edu.nju.method.impl;

import edn.nju.constant.Constant;
import edn.nju.enums.CompleteMethodEnum;
import edn.nju.util.MyMath;
import edu.nju.method.KNN;
import edu.nju.model.MachinePartialStatus;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/28 21:12
 * @description: TODO
 */

@Slf4j
@Service
public class KNNImpl implements KNN {
    @Override
    public List<MachinePartialStatus> partialKNN(List<MachinePartialStatus> selectedData) {
        List<MachinePartialStatus> res = new ArrayList<>();
        for (int i = 0; i < selectedData.size() - 1; i++) {
            MachinePartialStatus current = selectedData.get(i);
            //不存在的左邻居可用current代替
            MachinePartialStatus leftNeighbor1 = i - 2 >= 0 ? selectedData.get(i - 2) : current;
            MachinePartialStatus leftNeighbor2 = i - 1 >= 0 ? selectedData.get(i - 1) : current;
            MachinePartialStatus next = selectedData.get(i + 1);
            //不存在的右邻居可用next代替
            MachinePartialStatus rightNeighbor2 = i + 2 < selectedData.size() ?
                                                    selectedData.get(i + 2) : next;
            MachinePartialStatus rightNeighbor1 = i + 3 < selectedData.size() ?
                                                    selectedData.get(i + 3) : next;
            //近邻的连续值属性集合
            int[] dataList = {leftNeighbor1.getData(), leftNeighbor2.getData(), current.getData(),
                    next.getData(), rightNeighbor2.getData(), rightNeighbor1.getData()};
            //近邻的离散值属性集合
            Object[] blockFlagList = {leftNeighbor1.isBlockFlag(), leftNeighbor2.isBlockFlag(),
                    current.isBlockFlag(), next.isBlockFlag(), rightNeighbor2.isBlockFlag(),
                    rightNeighbor1.isBlockFlag()};

            long interval = next.getCreateAt() - current.getCreateAt();
            //计算两个时间点之前的时间间隔中可以插入多少数据
            int missingCount = (int) Math.ceil(interval /
                    (Constant.Completion.PARTIAL_INTERVAL + Constant.Completion.PARTIAL_BIAS)) - 1;
            //计算插入数据的时间点
            int insertInterval = missingCount > 0 ? (int) interval / (missingCount + 1) : 0;
            long createTime = current.getCreateAt();
            for (int j = 0; j < missingCount; j++) {
                createTime = createTime + insertInterval;
                MachinePartialStatus createdData = new MachinePartialStatus(
                        current.getUid(),
                        current.getName(),
                        MyMath.getAvgWithKNNWeight(dataList),
                        (boolean) MyMath.getMostValue(blockFlagList),
                        createTime,
                        CompleteMethodEnum.KNN.getCode()
                );
                res.add(createdData);
            }
        }
        return res;
    }

    @Override
    public List<MachineV2Status> v2KNN(List<MachineV2Status> selectedData) {
        List<MachineV2Status> res = new ArrayList<>();
        for (int i = 0; i < selectedData.size() - 1; i++) {
            MachineV2Status current = selectedData.get(i);
            //不存在的左邻居可用current代替
            MachineV2Status leftNeighbor1 = i - 2 >= 0 ? selectedData.get(i - 2) : current;
            MachineV2Status leftNeighbor2 = i - 1 >= 0 ? selectedData.get(i - 1) : current;
            MachineV2Status next = selectedData.get(i + 1);
            //不存在的右邻居可用next代替
            MachineV2Status rightNeighbor2 = i + 2 < selectedData.size() ?
                    selectedData.get(i + 2) : next;
            MachineV2Status rightNeighbor1 = i + 3 < selectedData.size() ?
                    selectedData.get(i + 3) : next;
            //近邻的连续值属性集合
            int[] pm25List = {leftNeighbor1.getPm2_5(), leftNeighbor2.getPm2_5(), current.getPm2_5(),
                    next.getPm2_5(), rightNeighbor2.getPm2_5(), rightNeighbor1.getPm2_5()};

            int[] tempList = {leftNeighbor1.getTemp(), leftNeighbor2.getTemp(), current.getTemp(),
                    next.getTemp(), rightNeighbor2.getTemp(), rightNeighbor1.getTemp()};

            int[] humidList = {leftNeighbor1.getHumid(), leftNeighbor2.getHumid(), current.getHumid(),
                    next.getHumid(), rightNeighbor2.getHumid(), rightNeighbor1.getHumid()};

            int[] co2List = {leftNeighbor1.getCo2(), leftNeighbor2.getCo2(), current.getCo2(),
                    next.getCo2(), rightNeighbor2.getCo2(), rightNeighbor1.getCo2()};

            int[] volumeList = {leftNeighbor1.getVolume(), leftNeighbor2.getVolume(), current.getVolume(),
                    next.getVolume(), rightNeighbor2.getVolume(), rightNeighbor1.getVolume()};
            //近邻的离散值属性集合
            Object[] powerList = {leftNeighbor1.getPower(), leftNeighbor2.getPower(), current.getPower(),
                    next.getPower(), rightNeighbor2.getPower(), rightNeighbor1.getPower()};

            Object[] modeList = {leftNeighbor1.getMode(), leftNeighbor2.getMode(), current.getMode(),
                    next.getMode(), rightNeighbor2.getMode(), rightNeighbor1.getMode()};

            Object[] heatList = {leftNeighbor1.getHeat(), leftNeighbor2.getHeat(), current.getHeat(),
                    next.getHeat(), rightNeighbor2.getHeat(), rightNeighbor1.getHeat()};

            Object[] lightList = {leftNeighbor1.getLight(), leftNeighbor2.getLight(), current.getLight(),
                    next.getLight(), rightNeighbor2.getLight(), rightNeighbor1.getLight()};

            Object[] lockList = {leftNeighbor1.getLock(), leftNeighbor2.getLock(), current.getLock(),
                    next.getLock(), rightNeighbor2.getLock(), rightNeighbor1.getLock()};

            Object[] blockFlagList = {leftNeighbor1.isBlockFlag(), leftNeighbor2.isBlockFlag(),
                    current.isBlockFlag(), next.isBlockFlag(), rightNeighbor2.isBlockFlag(),
                    rightNeighbor1.isBlockFlag()};

            long interval = next.getCreateAt() - current.getCreateAt();
            //计算两个时间点之前的时间间隔中可以插入多少数据
            int missingCount = (int) Math.ceil(interval /
                    (Constant.Completion.V2V3_INTERVAL + Constant.Completion.V2V3_BIAS)) - 1;
            //计算插入数据的时间点
            int insertInterval = missingCount > 0 ? (int) interval / (missingCount + 1) : 0;
            long createTime = current.getCreateAt();
            for (int j = 0; j < missingCount; j++) {
                createTime = createTime + insertInterval;
                MachineV2Status createdData = new MachineV2Status(
                        current.getUid(),
                        MyMath.getAvgWithKNNWeight(pm25List),
                        MyMath.getAvgWithKNNWeight(tempList),
                        MyMath.getAvgWithKNNWeight(humidList),
                        MyMath.getAvgWithKNNWeight(co2List),
                        MyMath.getAvgWithKNNWeight(volumeList),
                        (int) MyMath.getMostValue(powerList),
                        (int) MyMath.getMostValue(modeList),
                        (int) MyMath.getMostValue(heatList),
                        (int) MyMath.getMostValue(lightList),
                        (int) MyMath.getMostValue(lockList),
                        (boolean) MyMath.getMostValue(blockFlagList),
                        createTime,
                        CompleteMethodEnum.KNN.getCode()
                );
                res.add(createdData);
            }
        }
        return res;
    }

    @Override
    public List<MachineV3Status> v3KNN(List<MachineV3Status> selectedData) {
        List<MachineV3Status> res = new ArrayList<>();
        for (int i = 0; i < selectedData.size() - 1; i++) {
            MachineV3Status current = selectedData.get(i);
            //不存在的左邻居可用current代替
            MachineV3Status leftNeighbor1 = i - 2 >= 0 ? selectedData.get(i - 2) : current;
            MachineV3Status leftNeighbor2 = i - 1 >= 0 ? selectedData.get(i - 1) : current;
            MachineV3Status next = selectedData.get(i + 1);
            //不存在的右邻居可用next代替
            MachineV3Status rightNeighbor2 = i + 2 < selectedData.size() ?
                    selectedData.get(i + 2) : next;
            MachineV3Status rightNeighbor1 = i + 3 < selectedData.size() ?
                    selectedData.get(i + 3) : next;
            //近邻的连续值属性集合
            int[] pm25aList = {leftNeighbor1.getPm2_5a(), leftNeighbor2.getPm2_5a(), current.getPm2_5a(),
                    next.getPm2_5a(), rightNeighbor2.getPm2_5a(), rightNeighbor1.getPm2_5a()};

            int[] pm25bList = {leftNeighbor1.getPm2_5b(), leftNeighbor2.getPm2_5b(), current.getPm2_5b(),
                    next.getPm2_5b(), rightNeighbor2.getPm2_5b(), rightNeighbor1.getPm2_5b()};

            int[] tempInList = {leftNeighbor1.getTempIndoor(), leftNeighbor2.getTempIndoor(),
                    current.getTempIndoor(), next.getTempIndoor(), rightNeighbor2.getTempIndoor(),
                    rightNeighbor1.getTempIndoor()};

            int[] tempOutList = {leftNeighbor1.getTempOutdoor(), leftNeighbor2.getTempOutdoor(),
                    current.getTempOutdoor(), next.getTempOutdoor(), rightNeighbor2.getTempOutdoor(),
                    rightNeighbor1.getTempOutdoor()};

            int[] humidList = {leftNeighbor1.getHumidity(), leftNeighbor2.getHumidity(),
                    current.getHumidity(), next.getHumidity(), rightNeighbor2.getHumidity(),
                    rightNeighbor1.getHumidity()};

            int[] co2List = {leftNeighbor1.getCo2(), leftNeighbor2.getCo2(), current.getCo2(),
                    next.getCo2(), rightNeighbor2.getCo2(), rightNeighbor1.getCo2()};

            int[] volumeList = {leftNeighbor1.getVolume(), leftNeighbor2.getVolume(), current.getVolume(),
                    next.getVolume(), rightNeighbor2.getVolume(), rightNeighbor1.getVolume()};
            //近邻的离散值属性集合
            Object[] statusList = {leftNeighbor1.getStatus(), leftNeighbor2.getStatus(),
                    current.getStatus(), next.getStatus(), rightNeighbor2.getStatus(),
                    rightNeighbor1.getStatus()};

            Object[] modeList = {leftNeighbor1.getMode(), leftNeighbor2.getMode(), current.getMode(),
                    next.getMode(), rightNeighbor2.getMode(), rightNeighbor1.getMode()};

            Object[] heatList = {leftNeighbor1.getHeat(), leftNeighbor2.getHeat(), current.getHeat(),
                    next.getHeat(), rightNeighbor2.getHeat(), rightNeighbor1.getHeat()};

            Object[] lightList = {leftNeighbor1.getLight(), leftNeighbor2.getLight(), current.getLight(),
                    next.getLight(), rightNeighbor2.getLight(), rightNeighbor1.getLight()};

            Object[] lockList = {leftNeighbor1.getChildlock(), leftNeighbor2.getChildlock(),
                    current.getChildlock(), next.getChildlock(), rightNeighbor2.getChildlock(),
                    rightNeighbor1.getChildlock()};

            long interval = next.getCreateAt() - current.getCreateAt();
            //计算两个时间点之前的时间间隔中可以插入多少数据
            int missingCount = (int) Math.ceil(interval /
                    (Constant.Completion.V2V3_INTERVAL + Constant.Completion.V2V3_BIAS)) - 1;
            //计算插入数据的时间点
            int insertInterval = missingCount > 0 ? (int) interval / (missingCount + 1) : 0;
            long createTime = current.getCreateAt();
            for (int j = 0; j < missingCount; j++) {
                createTime = createTime + insertInterval;
                MachineV3Status createdData = new MachineV3Status(
                        current.getUid(),
                        MyMath.getAvgWithKNNWeight(pm25aList),
                        MyMath.getAvgWithKNNWeight(pm25bList),
                        MyMath.getAvgWithKNNWeight(tempInList),
                        MyMath.getAvgWithKNNWeight(tempOutList),
                        MyMath.getAvgWithKNNWeight(humidList),
                        MyMath.getAvgWithKNNWeight(co2List),
                        (int) MyMath.getMostValue(statusList),
                        (int) MyMath.getMostValue(modeList),
                        MyMath.getAvgWithKNNWeight(volumeList),
                        (int) MyMath.getMostValue(heatList),
                        (int) MyMath.getMostValue(lightList),
                        (int) MyMath.getMostValue(lockList),
                        createTime,
                        CompleteMethodEnum.KNN.getCode()
                );
                res.add(createdData);
            }
        }
        return res;
    }
}
