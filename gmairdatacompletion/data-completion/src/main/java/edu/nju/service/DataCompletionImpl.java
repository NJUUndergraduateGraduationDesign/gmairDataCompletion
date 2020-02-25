package edu.nju.service;

import edu.nju.method.Mean;
import edu.nju.model.MachineV2Status;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataCompletionImpl implements DataCompletion {

    private static final int pageSize = 10000;  //以10000个数据为一组进行补全
    private static final int period = 3600000;

    @Resource
    private MachineV2StatusService machineV2StatusService;

    @Resource
    private Mean mean;

    @Override
    public boolean partialCompletion() {
        return false;
    }

    @Override
    public boolean v1Completion() {
        return false;
    }

    @Override
    public boolean v2Completion() {
        List<MachineV2Status> missingData = new ArrayList<>();

        //将所有具有V2数据的设备进行数据补全
        List<String> allUids = machineV2StatusService.getAllUids();

        //这是按照时间间隔来补全的，暂时不采用
//        for (String oneUid : allUids) {
//            long firstRecodeTime = machineV2StatusService.getStartTimeByUid(oneUid);
//            System.out.println(firstRecodeTime);
//            long start = firstRecodeTime;
//            long end = firstRecodeTime + period;
//            List<MachineV2Status> selectedData;
//            while ((selectedData = machineV2StatusService.fetchBatchByUid(oneUid, start, end)).size() > 0) {
//                //这里调用补全方法
//                if (start == 1569781825880L)
//                    System.out.println(selectedData.get(0));
//                missingData.addAll(mean.v2Mean(selectedData));
//                start = end;
//                end = start + period;
//            }
//            break;
//        }
        int count = 0;
        for (String oneUid : allUids) {
            int pageIndex = 0;
            int currentPageSize = pageSize + 1; //让偶数组的数据多一个，可以不漏补任何数据
            Page<MachineV2Status> selectedData;

            while (!(selectedData =
                    machineV2StatusService.fetchBatchByUid(oneUid, pageIndex, currentPageSize))
                    .isEmpty()) {
                //这里调用补全方法
                count += selectedData.getSize();
                System.out.println("count: " + count + " size: " + selectedData.getSize());
                missingData.addAll(mean.v2Mean(selectedData.getContent()));
                pageIndex++;
                currentPageSize = (pageIndex % 2) == 0 ? pageSize + 1 : pageSize;
            }
            break;
        }
        System.out.println("missing data: " + missingData.size());
        System.out.println(missingData.get(0));
        //先不要存进数据库
//        machineV2StatusService.insertBatch(missingData);
        return true;
    }

    @Override
    public boolean v3Completion() {
        return false;
    }
}
