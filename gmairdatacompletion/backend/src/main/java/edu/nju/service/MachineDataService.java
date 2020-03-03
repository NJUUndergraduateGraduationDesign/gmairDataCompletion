package edu.nju.service;

import edu.nju.dto.NormalCompleteListDTO;
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
}
