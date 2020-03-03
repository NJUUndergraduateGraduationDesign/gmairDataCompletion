package edu.nju.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import edn.nju.enums.CompleteMethodEnum;
import edn.nju.util.TimeUtil;
import edu.nju.dto.NormalCompleteListDTO;
import edu.nju.dto.NormalCompleteMapDTO;
import edu.nju.dto.NormalCompletePm25DTO;
import edu.nju.model.status.*;
import edu.nju.request.LastNDayRequest;
import edu.nju.request.LastNHourRequest;
import edu.nju.service.*;
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
    @Resource
    PowerDailyService powerDailyService;
    @Resource
    PowerHourlyService powerHourlyService;
    @Resource
    HeatDailyService heatDailyService;
    @Resource
    HeatHourlyService heatHourlyService;
    @Resource
    VolumeDailyService volumeDailyService;
    @Resource
    VolumeHourlyService volumeHourlyService;
    @Resource
    HumidDailyService humidDailyService;
    @Resource
    HumidHourlyService humidHourlyService;
    @Resource
    TempDailyService tempDailyService;
    @Resource
    TempHourlyService tempHourlyService;
    @Resource
    ModeDailyService modeDailyService;
    @Resource
    ModeHourlyService modeHourlyService;

    @Override
    public NormalCompletePm25DTO getLastNDayPm25Daily(LastNDayRequest request) {
        return null;
    }

    @Override
    public NormalCompletePm25DTO getOneDayPm25Hourly(LastNHourRequest request) {
        return null;
    }

    @Override
    public NormalCompleteMapDTO getLastNDayModeDaily(LastNDayRequest request) {
        log.info("ModeLastNDayRequest:{}", request);
        long end = modeDailyService.getLatestTime(request.getUid());
        long start = TimeUtil.getNDayBefore(end, request.getLastNDay() - 1);

        List<ModeDaily> normalList =
                modeDailyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        int normalSleep = normalList.stream().mapToInt(ModeDaily::getSleepMinute).sum();
        int normalManual = normalList.stream().mapToInt(ModeDaily::getManualMinute).sum();
        int normalAuto = normalList.stream().mapToInt(ModeDaily::getAutoMinute).sum();
        Map<String, Object> normalMap = ImmutableMap.of("sleepMinute", normalSleep,
                "manualMinute", normalManual, "autoMinute", normalAuto);
        
        List<ModeDaily> completeList = Lists.newArrayList();
        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = modeDailyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        int completeSleep = completeList.stream().mapToInt(ModeDaily::getSleepMinute).sum();
        int completeManual = completeList.stream().mapToInt(ModeDaily::getManualMinute).sum();
        int completeAuto = completeList.stream().mapToInt(ModeDaily::getAutoMinute).sum();
        Map<String, Object> completeMap = ImmutableMap.of("sleepMinute", completeSleep,
                "manualMinute", completeManual, "autoMinute", completeAuto);

        return new NormalCompleteMapDTO(normalMap,completeMap);
    }

    @Override
    public NormalCompleteMapDTO getOneDayModeHourly(LastNHourRequest request) {
        log.info("ModeLastNHourRequest:{}", request);
        long start = request.getDate().getTime();
        long end = TimeUtil.endOfThisDay(start);

        List<ModeHourly> normalList =
                modeHourlyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        int normalSleep = normalList.stream().mapToInt(ModeHourly::getSleepMinute).sum();
        int normalManual = normalList.stream().mapToInt(ModeHourly::getManualMinute).sum();
        int normalAuto = normalList.stream().mapToInt(ModeHourly::getAutoMinute).sum();
        Map<String, Object> normalMap = ImmutableMap.of("sleepMinute", normalSleep,
                "manualMinute", normalManual, "autoMinute", normalAuto);

        List<ModeHourly> completeList = Lists.newArrayList();
        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = modeHourlyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        int completeSleep = completeList.stream().mapToInt(ModeHourly::getSleepMinute).sum();
        int completeManual = completeList.stream().mapToInt(ModeHourly::getManualMinute).sum();
        int completeAuto = completeList.stream().mapToInt(ModeHourly::getAutoMinute).sum();
        Map<String, Object> completeMap = ImmutableMap.of("sleepMinute", completeSleep,
                "manualMinute", completeManual, "autoMinute", completeAuto);

        return new NormalCompleteMapDTO(normalMap,completeMap);
    }

    @Override
    public NormalCompleteListDTO getLastNDayCo2Daily(LastNDayRequest request) {
        log.info("Co2LastNDayRequest:{}", request);
        long end = co2DailyService.getLatestTime(request.getUid());
        long start = TimeUtil.getNDayBefore(end, request.getLastNDay() - 1);

        List<Co2Daily> normalList =
                co2DailyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(Co2Daily::toDTOMap).collect(Collectors.toList());
        List<Co2Daily> completeList = Lists.newArrayList();

        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = co2DailyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(Co2Daily::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getOneDayCo2Hourly(LastNHourRequest request) {
        log.info("Co2LastNHourRequest:{}", request);
        long start = request.getDate().getTime();
        long end = TimeUtil.endOfThisDay(start);

        List<Co2Hourly> normalList =
                co2HourlyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(Co2Hourly::toDTOMap).collect(Collectors.toList());

        List<Co2Hourly> completeList = Lists.newArrayList();
        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = co2HourlyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(Co2Hourly::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getLastNDayPowerDaily(LastNDayRequest request) {
        log.info("PowerLastNDayRequest:{}", request);
        long end = powerDailyService.getLatestTime(request.getUid());
        long start = TimeUtil.getNDayBefore(end, request.getLastNDay() - 1);

        List<PowerDaily> normalList =
                powerDailyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(PowerDaily::toDTOMap).collect(Collectors.toList());
        List<PowerDaily> completeList = Lists.newArrayList();

        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = powerDailyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(PowerDaily::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getOneDayPowerHourly(LastNHourRequest request) {
        log.info("PowerLastNHourRequest:{}", request);
        long start = request.getDate().getTime();
        long end = TimeUtil.endOfThisDay(start);

        List<PowerHourly> normalList =
                powerHourlyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(PowerHourly::toDTOMap).collect(Collectors.toList());

        List<PowerHourly> completeList = Lists.newArrayList();
        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = powerHourlyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(PowerHourly::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getLastNDayHeatDaily(LastNDayRequest request) {
        log.info("HeatLastNDayRequest:{}", request);
        long end = heatDailyService.getLatestTime(request.getUid());
        long start = TimeUtil.getNDayBefore(end, request.getLastNDay() - 1);

        List<HeatDaily> normalList =
                heatDailyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(HeatDaily::toDTOMap).collect(Collectors.toList());
        List<HeatDaily> completeList = Lists.newArrayList();

        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = heatDailyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(HeatDaily::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getOneDayHeatHourly(LastNHourRequest request) {
        log.info("HeatLastNHourRequest:{}", request);
        long start = request.getDate().getTime();
        long end = TimeUtil.endOfThisDay(start);

        List<HeatHourly> normalList =
                heatHourlyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(HeatHourly::toDTOMap).collect(Collectors.toList());

        List<HeatHourly> completeList = Lists.newArrayList();
        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = heatHourlyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(HeatHourly::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getLastNDayVolumeDaily(LastNDayRequest request) {
        log.info("VolumeLastNDayRequest:{}", request);
        long end = volumeDailyService.getLatestTime(request.getUid());
        long start = TimeUtil.getNDayBefore(end, request.getLastNDay() - 1);

        List<VolumeDaily> normalList =
                volumeDailyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(VolumeDaily::toDTOMap).collect(Collectors.toList());
        List<VolumeDaily> completeList = Lists.newArrayList();

        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = volumeDailyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(VolumeDaily::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getOneDayVolumeHourly(LastNHourRequest request) {
        log.info("VolumeLastNHourRequest:{}", request);
        long start = request.getDate().getTime();
        long end = TimeUtil.endOfThisDay(start);

        List<VolumeHourly> normalList =
                volumeHourlyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(VolumeHourly::toDTOMap).collect(Collectors.toList());

        List<VolumeHourly> completeList = Lists.newArrayList();
        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = volumeHourlyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(VolumeHourly::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getLastNDayHumidDaily(LastNDayRequest request) {
        log.info("HumidLastNDayRequest:{}", request);
        long end = humidDailyService.getLatestTime(request.getUid());
        long start = TimeUtil.getNDayBefore(end, request.getLastNDay() - 1);

        List<HumidDaily> normalList =
                humidDailyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(HumidDaily::toDTOMap).collect(Collectors.toList());
        List<HumidDaily> completeList = Lists.newArrayList();

        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = humidDailyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(HumidDaily::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getOneDayHumidHourly(LastNHourRequest request) {
        log.info("HumidLastNHourRequest:{}", request);
        long start = request.getDate().getTime();
        long end = TimeUtil.endOfThisDay(start);

        List<HumidHourly> normalList =
                humidHourlyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(HumidHourly::toDTOMap).collect(Collectors.toList());

        List<HumidHourly> completeList = Lists.newArrayList();
        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = humidHourlyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(HumidHourly::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getLastNDayTempDaily(LastNDayRequest request) {
        log.info("TempLastNDayRequest:{}", request);
        long end = tempDailyService.getLatestTime(request.getUid());
        long start = TimeUtil.getNDayBefore(end, request.getLastNDay() - 1);

        List<TempDaily> normalList =
                tempDailyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(TempDaily::toDTOMap).collect(Collectors.toList());
        List<TempDaily> completeList = Lists.newArrayList();

        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = tempDailyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(TempDaily::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

    @Override
    public NormalCompleteListDTO getOneDayTempHourly(LastNHourRequest request) {
        log.info("TempLastNHourRequest:{}", request);
        long start = request.getDate().getTime();
        long end = TimeUtil.endOfThisDay(start);

        List<TempHourly> normalList =
                tempHourlyService.getByUidAndCompleteMethod(request.getUid(), CompleteMethodEnum.NONE.getCode(), start, end);
        List<Map<String, Object>> normal = normalList.stream().map(TempHourly::toDTOMap).collect(Collectors.toList());

        List<TempHourly> completeList = Lists.newArrayList();
        if (CompleteMethodEnum.isCompleteMethodCode(request.getCompleteType())) {
            completeList = tempHourlyService.getByUidAndCompleteMethod(request.getUid(), request.getCompleteType(), start, end);
        }
        List<Map<String, Object>> complete = completeList.stream().map(TempHourly::toDTOMap).collect(Collectors.toList());

        return new NormalCompleteListDTO(normal, complete);
    }

}
