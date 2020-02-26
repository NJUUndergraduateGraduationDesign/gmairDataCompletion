package edu.nju.service;

import edu.nju.method.Mean;
import edu.nju.method.UsePrevious;
import edu.nju.model.MachineV2Status;
import edu.nju.model.MachineV3Status;
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
    private MachineV3StatusService machineV3StatusService;

    @Resource
    private Mean mean;
    @Resource
    private UsePrevious usePrevious;

    @Override
    public void partialCompletion() {

    }

    @Override
    public void v1Completion() {

    }

    @Override
    public void v2Completion() {
        List<MachineV2Status> missingDataByMean = new ArrayList<>();
        List<MachineV2Status> missingDataByUsePrevious = new ArrayList<>();

        //将所有设备数据进行补全
        //这边可以开多线程，多个uid同时进行补全
        //但是开多线程之后就无法将上一组的最后一条数据给下一组了
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

                //这里调用所有补全方法，这边多遍历了一遍
                missingDataByMean.addAll(mean.v2Mean(selectedDataContent));
                missingDataByUsePrevious.addAll(usePrevious.v2UsePrevious(selectedDataContent));
                pageIndex++;
            }
            //需要测试此类的时候可以在此处加上break语句，只跑一个uid，节省时间
            break;
        }
        System.out.println("Missing data created by MEAN: " + missingDataByMean.size() +
                "\nFirst missing data: " + missingDataByMean.get(0));
        System.out.println("Missing data created by USE_PREVIOUS: " + missingDataByUsePrevious.size() +
                "\nFirst missing data: " + missingDataByUsePrevious.get(0));
        //先不要存进数据库
//        machineV2StatusService.insertBatch(missingData);
    }

    @Override
    public void v3Completion() {
        List<MachineV3Status> missingDataByMean = new ArrayList<>();
        List<MachineV3Status> missingDataByUsePrevious = new ArrayList<>();

        //将所有设备数据进行补全
        //这边可以开多线程，多个uid同时进行补全
        //但是开多线程之后就无法将上一组的最后一条数据给下一组了
        List<String> allUids = machineV3StatusService.getAllUids();
        for (String oneUid : allUids) {
            int pageIndex = 0;
            Page<MachineV3Status> selectedData;
            List<MachineV3Status> selectedDataContent = new ArrayList<>();
            //将前一组数据的最后一条数据给下一组数据，可以做到不漏补任何一个数据
            MachineV3Status lastDataInPage = new MachineV3Status();

            while ((selectedData =
                    machineV3StatusService.fetchBatchByUid(oneUid, pageIndex, pageSize))
                    .hasContent()) {
                selectedDataContent.clear();

                if (pageIndex > 0) {
                    selectedDataContent.add(lastDataInPage);
                }
                selectedDataContent.addAll(selectedData.getContent());
                //将最后一条数据赋值给lastDataInPage
                lastDataInPage = selectedDataContent.get(selectedDataContent.size() - 1);

                //这里调用补全方法
                missingDataByMean.addAll(mean.v3Mean(selectedDataContent));
                missingDataByUsePrevious.addAll(usePrevious.v3UsePrevious(selectedDataContent));
                pageIndex++;
            }
            //需要测试此类的时候可以在此处加上break语句，只跑一个uid，节省时间
            break;
        }
        System.out.println("Missing data created by MEAN: " + missingDataByMean.size() +
                "\nFirst missing data: " + missingDataByMean.get(0));
        System.out.println("Missing data created by USE_PREVIOUS: " + missingDataByUsePrevious.size() +
                "\nFirst missing data: " + missingDataByUsePrevious.get(0));
        //先不要存进数据库
//        machineV3StatusService.insertBatch(missingData);
    }
}
