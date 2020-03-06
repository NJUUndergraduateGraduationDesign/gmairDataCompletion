package edu.nju.asyncTask;

import edu.nju.method.Mean;
import edu.nju.method.UsePrevious;
import edu.nju.model.MachineV3Status;
import edu.nju.service.MachineV3StatusService;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bright Chan
 * @date 2020/2/27 12:00
 */

@Service
public class V3CompletionThread {

    @Resource
    private MachineV3StatusService machineV3StatusService;
    @Resource
    private Mean mean;
    @Resource
    private UsePrevious usePrevious;

    @Async
    public void iterateAndComplete(String uid, int pageSize) {
        List<MachineV3Status> missingDataByMean = new ArrayList<>();
        List<MachineV3Status> missingDataByUsePrevious = new ArrayList<>();
        int pageIndex = 0;

        Page<MachineV3Status> selectedData;
        MachineV3Status lastDataInPage = new MachineV3Status();

        while ((selectedData =
                machineV3StatusService.fetchBatchByUid(uid, pageIndex, pageSize))
                .hasContent()) {
            List<MachineV3Status> selectedDataContent = new ArrayList<>();

            if (pageIndex > 0) {
                selectedDataContent.add(lastDataInPage);
            }
            selectedDataContent.addAll(selectedData.getContent());
            lastDataInPage = selectedDataContent.get(selectedDataContent.size() - 1);

            missingDataByMean.addAll(mean.v3Mean(selectedDataContent));
            missingDataByUsePrevious.addAll(usePrevious.v3UsePrevious(selectedDataContent));
            pageIndex++;
        }
        System.out.println("Missing data created by MEAN: " + missingDataByMean.size() +
                "\nFirst missing data: " + missingDataByMean.get(0));
        System.out.println("Missing data created by USE_PREVIOUS: " + missingDataByUsePrevious.size() +
                "\nFirst missing data: " + missingDataByUsePrevious.get(0));
        //先不要存进数据库
        machineV3StatusService.insertBatch(missingDataByMean);
        machineV3StatusService.insertBatch(missingDataByUsePrevious);
    }
}
