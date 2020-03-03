package edu.nju.service.impl;

import edn.nju.enums.CompleteMethodEnum;
import edn.nju.util.TimeUtil;
import edu.nju.dto.NormalCompleteListDTO;
import edu.nju.model.status.Co2Daily;
import edu.nju.model.status.Co2Hourly;
import edu.nju.request.LastNDayRequest;
import edu.nju.request.LastNHourRequest;
import edu.nju.service.Co2DailyService;
import edu.nju.service.Co2HourlyService;
import edu.nju.service.MachineDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 23:25
 * @description：
 */

@Slf4j
@Service
public class MachineDateServiceImpl implements MachineDataService {
    @Resource
    Co2DailyService co2DailyService;
    @Resource
    Co2HourlyService co2HourlyService;

    @Override
    public NormalCompleteListDTO getLastNDayCo2Daily(LastNDayRequest request) {
        log.info("Co2LastNDayRequest:{}",request);
        long end = co2DailyService.getLatestTime(request.getUid());
        long start = TimeUtil.getNDayBefore(end, request.getLastNDay()-1);

        List<Co2Daily> completeList=
                co2DailyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        List<Co2Daily> normalList=
                co2DailyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(),start,end);
        List<Map<String,Object>> complete = completeList.stream().map(Co2Daily::toDTOMap).collect(Collectors.toList());
        List<Map<String,Object>> normal = normalList.stream().map(Co2Daily::toDTOMap).collect(Collectors.toList());
        return new NormalCompleteListDTO(normal,complete);
    }

    @Override
    public NormalCompleteListDTO getOneDayCo2Hourly(LastNHourRequest request) {
        log.info("Co2LastNHourRequest:{}",request);
        long start = request.getDate().getTime();
        long end = TimeUtil.endOfThisDay(start);

        List<Co2Hourly> completeList=
                co2HourlyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        List<Co2Hourly> normalList=
                co2HourlyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(),start,end);
        List<Map<String,Object>> complete = completeList.stream().map(Co2Hourly::toDTOMap).collect(Collectors.toList());
        List<Map<String,Object>> normal = normalList.stream().map(Co2Hourly::toDTOMap).collect(Collectors.toList());
        return new NormalCompleteListDTO(normal,complete);
    }
}
