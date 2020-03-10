package edu.nju.controller;

import edn.nju.ResponseDTO;
import edn.nju.enums.CompleteMethodEnum;
import edu.nju.dto.NormalCompleteListDTO;
import edu.nju.dto.NormalCompleteMapDTO;
import edu.nju.dto.NormalCompletePm25DTO;
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

    @PostMapping("/pm25/lastNDay")
    public ResponseDTO getLastNDayPm25(@RequestBody LastNDayRequest request) {
        if (checkLastNDayRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompletePm25DTO dto = machineDataServiceImpl.getLastNDayPm25Daily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/pm25/lastNHour")
    public ResponseDTO getLastNHourPm25(@RequestBody LastNHourRequest request) {
        if (checkLastNHourRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompletePm25DTO dto = machineDataServiceImpl.getOneDayPm25Hourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/mode/lastNDay")
    public ResponseDTO getLastNDayMode(@RequestBody LastNDayRequest request) {
        if (checkLastNDayRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteMapDTO dto = machineDataServiceImpl.getLastNDayModeDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/mode/lastNHour")
    public ResponseDTO getLastNHourMode(@RequestBody LastNHourRequest request) {
        System.out.println(request.getDate());
        if (checkLastNHourRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteMapDTO dto = machineDataServiceImpl.getOneDayModeHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

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

    @PostMapping("/power/lastNDay")
    public ResponseDTO getLastNDayPower(@RequestBody LastNDayRequest request) {
        if (checkLastNDayRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayPowerDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/power/lastNHour")
    public ResponseDTO getLastNHourPower(@RequestBody LastNHourRequest request) {
        if (checkLastNHourRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayPowerHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/heat/lastNDay")
    public ResponseDTO getLastNDayHeat(@RequestBody LastNDayRequest request) {
        if (checkLastNDayRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayHeatDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/heat/lastNHour")
    public ResponseDTO getLastNHourHeat(@RequestBody LastNHourRequest request) {
        if (checkLastNHourRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayHeatHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/volume/lastNDay")
    public ResponseDTO getLastNDayVolume(@RequestBody LastNDayRequest request) {
        if (checkLastNDayRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayVolumeDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/volume/lastNHour")
    public ResponseDTO getLastNHourVolume(@RequestBody LastNHourRequest request) {
        if (checkLastNHourRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayVolumeHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/humid/lastNDay")
    public ResponseDTO getLastNDayHumid(@RequestBody LastNDayRequest request) {
        if (checkLastNDayRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayHumidDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/humid/lastNHour")
    public ResponseDTO getLastNHourHumid(@RequestBody LastNHourRequest request) {
        if (checkLastNHourRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayHumidHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/temp/lastNDay")
    public ResponseDTO getLastNDayTemp(@RequestBody LastNDayRequest request) {
        if (checkLastNDayRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayTempDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/temp/lastNHour")
    public ResponseDTO getLastNHourTemp(@RequestBody LastNHourRequest request) {
        if (checkLastNHourRequestInvalid(request)) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayTempHourly(request);
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
