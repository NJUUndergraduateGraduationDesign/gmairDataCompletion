package edu.nju.controller;

import edn.nju.ResponseDTO;
import edn.nju.enums.CompleteMethodEnum;
import edu.nju.dto.NormalCompleteListDTO;
import edu.nju.request.LastNDayRequest;
import edu.nju.request.LastNHourRequest;
import edu.nju.service.MachineDataService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 22:55
 * @description：
 */

@RestController
@RequestMapping("/machine/data")
public class MachineDataController {
    @Resource
    MachineDataService machineDataServiceImpl;

    @PostMapping("/co2/lastNDay")
    public ResponseDTO getLastNDayCo2(@RequestBody LastNDayRequest request) {
        if (checkLastNDayRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayCo2Daily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/co2/lastNHour")
    public ResponseDTO getLastNHourCo2(@RequestBody LastNHourRequest request) {
        if (checkLastNHourRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayCo2Hourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    private boolean checkLastNDayRequestInvalid(LastNDayRequest request) {
        return (request == null
                || StringUtils.isEmpty(request.getUid())
                || request.getLastNDay() < 0
                || !CompleteMethodEnum.isValidCode(request.getCompleteType()));
    }

    private boolean checkLastNHourRequestInvalid(LastNHourRequest request) {
        return (request == null
                || StringUtils.isEmpty(request.getUid())
                || !CompleteMethodEnum.isValidCode(request.getCompleteType()));
    }
}
