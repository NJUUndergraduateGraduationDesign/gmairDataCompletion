package edu.nju.service;

import edu.nju.asyncTask.PartialCompletionThread;
import edu.nju.asyncTask.V2CompletionThread;
import edu.nju.asyncTask.V3CompletionThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataCompletionImpl implements DataCompletion {

    private static final int pageSize = 10000;  //以10000个数据为一组进行补全
    private static final long timePerBatch = 10;

    @Resource
    private MachinePartialStatusService machinePartialStatusService;
    @Resource
    private MachineV2StatusService machineV2StatusService;
    @Resource
    private MachineV3StatusService machineV3StatusService;

    @Resource
    private PartialCompletionThread partialCompletionThread;
    @Resource
    private V2CompletionThread v2CompletionThread;
    @Resource
    private V3CompletionThread v3CompletionThread;

    @Override
    public void partialCompletion() {
        //将所有设备数据进行补全
        //这边可以开多线程，多个uid同时进行补全
        List<String> allUids = machinePartialStatusService.getAllUids();
        for (String oneUid : allUids) {
            partialCompletionThread.iterateAndComplete(oneUid, pageSize);
            //需要测试此类的时候可以在此处加上break语句，只跑一个uid，节省时间
            break;
        }
    }

    @Override
    public void partialCompletion(List<String> uidList) {
        for (String oneUid : uidList) {
            partialCompletionThread.iterateAndComplete(oneUid, pageSize);
        }
    }

    @Override
    public void v2Completion() {
        //将所有设备数据进行补全
        //这边可以开多线程，多个uid同时进行补全
        List<String> allUids = machineV2StatusService.getAllUids();
        for (String oneUid : allUids) {
            v2CompletionThread.iterateAndComplete(oneUid, pageSize);
            //需要测试此类的时候可以在此处加上break语句，只跑一个uid，节省时间
            break;
        }
    }

    @Override
    public void v2Completion(List<String> uidList) {
        for (String oneUid : uidList) {
            v2CompletionThread.iterateAndComplete(oneUid, pageSize);
        }
    }

    @Override
    public void v3Completion() {
        //将所有设备数据进行补全
        //这边可以开多线程，多个uid同时进行补全
        List<String> allUids = machineV3StatusService.getAllUids();
        for (String oneUid : allUids) {
            v3CompletionThread.iterateAndComplete(oneUid, pageSize);
            //需要测试此类的时候可以在此处加上break语句，只跑一个uid，节省时间
            break;
        }
    }

    @Override
    public void v3Completion(List<String> uidList) {
        for (String oneUid : uidList) {
            v3CompletionThread.iterateAndComplete(oneUid, pageSize);
        }
    }
}
