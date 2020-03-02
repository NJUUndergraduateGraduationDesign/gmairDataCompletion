package edu.nju.service;

import edu.nju.model.status.Co2Daily;
import edu.nju.request.LastNDayRequest;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 23:25
 * @description：
 */

public interface MachineDataService {
    List<Co2Daily> getLastNDayCo2Daily(LastNDayRequest request);
}
