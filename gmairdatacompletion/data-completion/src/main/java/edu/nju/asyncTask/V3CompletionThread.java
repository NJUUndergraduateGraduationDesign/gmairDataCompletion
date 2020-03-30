package edu.nju.asyncTask;

import edu.nju.method.KNN;
import edu.nju.method.Mean;
import edu.nju.method.UsePrevious;
import edu.nju.model.MachineV3Status;
import edu.nju.service.MachineV3StatusService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class V3CompletionThread {

    @Resource
    private MachineV3StatusService machineV3StatusService;
    @Resource
    private Mean mean;
    @Resource
    private UsePrevious usePrevious;
    @Resource
    private KNN knn;

    @Async
    public void iterateAndComplete(String uid, int pageSize) {
        log.info("current thread:{},uid{}" + Thread.currentThread().getName(),uid);

        List<MachineV3Status> missingDataByMean = new ArrayList<>();
        List<MachineV3Status> missingDataByUsePrevious = new ArrayList<>();
        List<MachineV3Status> missingDataByKNN = new ArrayList<>();
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
            log.info("pageIndex:{},pageSize:{},contentSize:{}",pageIndex,pageSize,selectedDataContent.size());

            missingDataByMean.addAll(mean.v3Mean(selectedDataContent));
            missingDataByUsePrevious.addAll(usePrevious.v3UsePrevious(selectedDataContent));
            missingDataByKNN.addAll(knn.v3KNN(selectedDataContent));
            pageIndex++;
        }

        log.info("missing data created by MEAN:{}",missingDataByMean.size());
        log.info("missing data created by UserPrevious:{}",missingDataByUsePrevious.size());
        log.info("missing data created by KNN:{}",missingDataByKNN.size());

        machineV3StatusService.insertBatch(missingDataByMean);
        machineV3StatusService.insertBatch(missingDataByUsePrevious);
        machineV3StatusService.insertBatch(missingDataByKNN);
    }
}
