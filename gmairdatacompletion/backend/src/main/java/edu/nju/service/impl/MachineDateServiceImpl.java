package edu.nju.service.impl;

import edn.nju.util.TimeUtil;
import edu.nju.model.status.Co2Daily;
import edu.nju.request.LastNDayRequest;
import edu.nju.service.Co2DailyService;
import edu.nju.service.MachineDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 23:25
 * @description：
 */

@Service
public class MachineDateServiceImpl implements MachineDataService {
    @Resource
    Co2DailyService co2DailyService;

    @Override
    public List<Co2Daily> getLastNDayCo2Daily(LastNDayRequest request) {
        //todo:获取最近记录时间
        long endTime = 1569772800000L;
        long end = TimeUtil.startOfNextDay(endTime);
        long start = TimeUtil.getNDayBefore(end, request.getLastNDay());
        return co2DailyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
    }
}
