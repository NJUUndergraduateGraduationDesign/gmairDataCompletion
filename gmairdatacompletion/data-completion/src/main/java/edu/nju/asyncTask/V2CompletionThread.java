package edu.nju.asyncTask;

import edu.nju.method.Mean;
import edu.nju.method.UsePrevious;
import edu.nju.model.MachineV2Status;
import edu.nju.service.MachineV2StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bright Chan
 * @date 2020/2/27 11:35
 */

@Slf4j
@Service
public class V2CompletionThread {

    private static final long timeInterval = 300000;
    private static final long timeBias     = 20000;

    @Resource
    private MachineV2StatusService machineV2StatusService;
    @Resource
    private Mean mean;
    @Resource
    private UsePrevious usePrevious;

    @Async
    public void iterateAndComplete(String uid, int pageSize) {
        log.info("current thread:{},uid{}" + Thread.currentThread().getName(),uid);
        //一个uid的所有缺失数据集合
        List<MachineV2Status> missingDataByMean = new ArrayList<>();
        List<MachineV2Status> missingDataByUsePrevious = new ArrayList<>();
        //分页查询中的页索引
        int pageIndex = 0;
        //选中的一段数据
        Page<MachineV2Status> selectedData;
        //将前一组数据的最后一条数据给下一组数据，可以做到不漏补任何一个数据
        MachineV2Status lastDataInPage = new MachineV2Status();

        //遍历一个uid的所有数据
        while ((selectedData =
                machineV2StatusService.fetchBatchByUid(uid, pageIndex, pageSize))
                .hasContent()) {
            List<MachineV2Status> selectedDataContent = new ArrayList<>();
            //从第二组数据开始，每一组数据都要加上前一组数据的最后一条数据
            if (pageIndex > 0) {
                selectedDataContent.add(lastDataInPage);
            }
            selectedDataContent.addAll(selectedData.getContent());
            //将最后一条数据赋值给lastDataInPage，以供下一组数据使用
            lastDataInPage = selectedDataContent.get(selectedDataContent.size() - 1);
            log.info("pageIndex:{},pageSize:{},contentSize:{}",pageIndex,pageSize,selectedDataContent.size());

            //这里调用所有补全方法，这边多遍历了一遍
            missingDataByMean.addAll(mean.v2Mean(selectedDataContent));
            missingDataByUsePrevious.addAll(usePrevious.v2UsePrevious(selectedDataContent));
            pageIndex++;
        }

        log.info("missing data created by MEAN:{}",missingDataByMean.size());
        log.info("missing data created by UserPrevious:{}",missingDataByUsePrevious.size());
        //先不要存进数据库
        machineV2StatusService.insertBatch(missingDataByMean);
        machineV2StatusService.insertBatch(missingDataByUsePrevious);
    }

    @Async
    public void iterateAndComplete(String uid, long timePerBatch) {
        log.info("current thread:{},uid{}" + Thread.currentThread().getName(),uid);
        //一个uid的所有缺失数据集合
        List<MachineV2Status> missingDataByMean = new ArrayList<>();
        List<MachineV2Status> missingDataByUsePrevious = new ArrayList<>();
        //查询的起止时间
        long startTime = machineV2StatusService.getStartTimeByUid(uid);
        long endTime   = machineV2StatusService.getLatestTimeByUid(uid);
        //选中的一段数据
        List<MachineV2Status> selectedData;

        //遍历一个uid的所有数据
        if ((selectedData =
                machineV2StatusService.fetchBatchByUid(uid, startTime, endTime, timeInterval, timeBias))
                .size() > 0) {
            //这里调用所有补全方法，这边多遍历了一遍
            missingDataByMean.addAll(mean.v2Mean(selectedData));
            missingDataByUsePrevious.addAll(usePrevious.v2UsePrevious(selectedData));
            startTime = endTime;
            endTime   = startTime + timePerBatch;
        }

        log.info("missing data created by MEAN:{}",missingDataByMean.size());
        log.info("missing data created by UserPrevious:{}",missingDataByUsePrevious.size());
        //先不要存进数据库
//        machineV2StatusService.insertBatch(missingDataByMean);
//        machineV2StatusService.insertBatch(missingDataByUsePrevious);
    }
}
