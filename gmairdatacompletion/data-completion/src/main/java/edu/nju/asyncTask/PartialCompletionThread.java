package edu.nju.asyncTask;

import edu.nju.method.KNN;
import edu.nju.method.Mean;
import edu.nju.method.UsePrevious;
import edu.nju.model.MachinePartialStatus;
import edu.nju.service.MachinePartialStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/2 10:43
 * @description: TODO
 */

@Slf4j
@Service
public class PartialCompletionThread {

    @Resource
    private MachinePartialStatusService machinePartialStatusService;
    @Resource
    private Mean mean;
    @Resource
    private UsePrevious usePrevious;
    @Resource
    private KNN knn;

    @Async
    public void iterateAndComplete(String uid, int pageSize) {
        log.info("current thread:{},uid{}" + Thread.currentThread().getName(),uid);
        //一个uid的所有缺失数据集合
        List<MachinePartialStatus> missingDataByMean = new ArrayList<>();
        List<MachinePartialStatus> missingDataByUsePrevious = new ArrayList<>();
        List<MachinePartialStatus> missingDataByKNN = new ArrayList<>();
        //分页查询中的页索引
        int pageIndex = 0;
        //选中的一段数据
        Page<MachinePartialStatus> selectedData;
        //将前一组数据的最后一条数据给下一组数据，可以做到不漏补任何一个数据
        MachinePartialStatus lastDataInPage = new MachinePartialStatus();

        //遍历一个uid的所有数据
        while ((selectedData =
                machinePartialStatusService.fetchBatchByUid(uid, pageIndex, pageSize))
                .hasContent()) {
            List<MachinePartialStatus> selectedDataContent = new ArrayList<>();

            //从第二组数据开始，每一组数据都要加上前一组数据的最后一条数据
            if (pageIndex > 0) {
                selectedDataContent.add(lastDataInPage);
            }
            selectedDataContent.addAll(selectedData.getContent());
            //将最后一条数据赋值给lastDataInPage，以供下一组数据使用
            lastDataInPage = selectedDataContent.get(selectedDataContent.size() - 1);
            log.info("pageIndex:{},pageSize:{},contentSize:{}",pageIndex,pageSize,selectedDataContent.size());

            //这里调用所有补全方法
            missingDataByMean.addAll(mean.partialMean(selectedDataContent));
            missingDataByUsePrevious.addAll(usePrevious.partialUsePrevious(selectedDataContent));
            missingDataByKNN.addAll(knn.partialKNN(selectedDataContent));
            pageIndex++;
        }

        log.info("missing data created by MEAN:{}",missingDataByMean.size());
        log.info("missing data created by UserPrevious:{}",missingDataByUsePrevious.size());
        log.info("missing data created by KNN:{}",missingDataByKNN.size());

        machinePartialStatusService.insertBatch(missingDataByMean);
        machinePartialStatusService.insertBatch(missingDataByUsePrevious);
        machinePartialStatusService.insertBatch(missingDataByKNN);
    }
}
