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
        for (String oneUid : allUids) {
            int pageIndex = 0;
            Page<MachineV2Status> selectedData;
            List<MachineV2Status> selectedDataContent = new ArrayList<>();
            //将前一组数据的最后一条数据给下一组数据，可以做到不漏补任何一个数据
            MachineV2Status lastDataInPage = new MachineV2Status();

            while ((selectedData =
                    machineV2StatusService.fetchBatchByUid(oneUid, pageIndex, pageSize))
                    .hasContent()) {
                selectedDataContent.clear();

                if (pageIndex > 0) {
                    selectedDataContent.add(lastDataInPage);
                }
                selectedDataContent.addAll(selectedData.getContent());
                //将最后一条数据赋值给lastDataInPage
                lastDataInPage = selectedDataContent.get(selectedDataContent.size() - 1);

                //这里调用补全方法
                missingData.addAll(mean.v2Mean(selectedDataContent));
                pageIndex++;
            }
            //需要测试此类的时候可以在此处加上break语句，只跑一个uid，节省时间
            //break;
        }
        System.out.println("missing data: " + missingData.size());
        //先不要存进数据库
//        machineV2StatusService.insertBatch(missingData);
        return true;
    }

    @Override
    public boolean v3Completion() {
        return false;
    }
}
