package edu.nju.service;

import edu.nju.dto.NormalCompleteListDTO;
import edu.nju.dto.NormalCompleteMapDTO;
import edu.nju.dto.NormalCompletePm25DTO;
import edu.nju.request.LastNDayRequest;
import edu.nju.request.LastNHourRequest;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 23:25
 * @description：
 */

public interface MachineDataService {
    NormalCompleteListDTO getLastNDayCo2Daily(LastNDayRequest request);

    NormalCompleteListDTO getOneDayCo2Hourly(LastNHourRequest request);

    NormalCompleteListDTO getLastNDayPowerDaily(LastNDayRequest request);

    NormalCompleteListDTO getOneDayPowerHourly(LastNHourRequest request);

    NormalCompleteListDTO getLastNDayHeatDaily(LastNDayRequest request);

    NormalCompleteListDTO getOneDayHeatHourly(LastNHourRequest request);

    NormalCompleteListDTO getLastNDayVolumeDaily(LastNDayRequest request);

    NormalCompleteListDTO getOneDayVolumeHourly(LastNHourRequest request);

    NormalCompleteListDTO getLastNDayHumidDaily(LastNDayRequest request);

    NormalCompleteListDTO getOneDayHumidHourly(LastNHourRequest request);

    NormalCompleteListDTO getLastNDayTempDaily(LastNDayRequest request);

    NormalCompleteListDTO getOneDayTempHourly(LastNHourRequest request);

    NormalCompletePm25DTO getLastNDayPm25Daily(LastNDayRequest request);

    NormalCompletePm25DTO getOneDayPm25Hourly(LastNHourRequest request);

    NormalCompleteMapDTO getLastNDayModeDaily(LastNDayRequest request);

    NormalCompleteMapDTO getOneDayModeHourly(LastNHourRequest request);
}
