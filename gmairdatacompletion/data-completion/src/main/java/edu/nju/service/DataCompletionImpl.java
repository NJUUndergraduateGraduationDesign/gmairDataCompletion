package edu.nju.service;

import edu.nju.asyncTask.V2CompletionThread;
import edu.nju.asyncTask.V3CompletionThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataCompletionImpl implements DataCompletion {

    private static final int pageSize = 10000;  //以10000个数据为一组进行补全

    @Resource
    private MachineV2StatusService machineV2StatusService;
    @Resource
    private MachineV3StatusService machineV3StatusService;

    @Resource
    private V2CompletionThread v2CompletionThread;
    @Resource
    private V3CompletionThread v3CompletionThread;

    @Override
    public void partialCompletion() {

    }

    @Override
    public void v1Completion() {

    }

    @Override
    public void v2Completion() {
        //将所有设备数据进行补全
        //这边可以开多线程，多个uid同时进行补全
        int count = 0;
        List<String> allUids = machineV2StatusService.getAllUids();
        for (String oneUid : allUids) {
            count++;
            v2CompletionThread.iterateAndComplete(oneUid, pageSize);
            if (count == 5)
                break;
            //需要测试此类的时候可以在此处加上break语句，只跑一个uid，节省时间
//            break;
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
}
