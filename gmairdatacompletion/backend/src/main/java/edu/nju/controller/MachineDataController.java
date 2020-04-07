package edu.nju.controller;

import edn.nju.ResponseDTO;
import edn.nju.enums.CompleteMethodEnum;
import edn.nju.enums.UserRoleEnum;
import edu.nju.dto.NormalCompleteListDTO;
import edu.nju.dto.NormalCompleteMapDTO;
import edu.nju.dto.NormalCompletePm25DTO;
import edu.nju.exception.ParamErrorException;
import edu.nju.request.LastNDayRequest;
import edu.nju.request.LastNHourRequest;
import edu.nju.service.MachineDataService;
import edu.nju.shiro.ShiroUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
@RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
public class MachineDataController {
    @Resource
    MachineDataService machineDataServiceImpl;

    @PostMapping("/pm25/lastNDay")
    public ResponseDTO getLastNDayPm25(@RequestBody LastNDayRequest request) {
        checkLastNDayRequest(request);
        NormalCompletePm25DTO dto = machineDataServiceImpl.getLastNDayPm25Daily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/pm25/lastNHour")
    public ResponseDTO getLastNHourPm25(@RequestBody LastNHourRequest request) {
        checkLastNHourRequest(request);
        NormalCompletePm25DTO dto = machineDataServiceImpl.getOneDayPm25Hourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/mode/lastNDay")
    public ResponseDTO getLastNDayMode(@RequestBody LastNDayRequest request) {
        checkLastNDayRequest(request);
        NormalCompleteMapDTO dto = machineDataServiceImpl.getLastNDayModeDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/mode/lastNHour")
    public ResponseDTO getLastNHourMode(@RequestBody LastNHourRequest request) {
        checkLastNHourRequest(request);
        NormalCompleteMapDTO dto = machineDataServiceImpl.getOneDayModeHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/co2/lastNDay")
    public ResponseDTO getLastNDayCo2(@RequestBody LastNDayRequest request) {
        checkLastNDayRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayCo2Daily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/co2/lastNHour")
    public ResponseDTO getLastNHourCo2(@RequestBody LastNHourRequest request) {
        checkLastNHourRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayCo2Hourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/power/lastNDay")
    public ResponseDTO getLastNDayPower(@RequestBody LastNDayRequest request) {
        checkLastNDayRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayPowerDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/power/lastNHour")
    public ResponseDTO getLastNHourPower(@RequestBody LastNHourRequest request) {
        checkLastNHourRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayPowerHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/heat/lastNDay")
    public ResponseDTO getLastNDayHeat(@RequestBody LastNDayRequest request) {
        checkLastNDayRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayHeatDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/heat/lastNHour")
    public ResponseDTO getLastNHourHeat(@RequestBody LastNHourRequest request) {
        checkLastNHourRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayHeatHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/volume/lastNDay")
    public ResponseDTO getLastNDayVolume(@RequestBody LastNDayRequest request) {
        checkLastNDayRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayVolumeDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/volume/lastNHour")
    public ResponseDTO getLastNHourVolume(@RequestBody LastNHourRequest request) {
        checkLastNHourRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayVolumeHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/humid/lastNDay")
    public ResponseDTO getLastNDayHumid(@RequestBody LastNDayRequest request) {
        checkLastNDayRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayHumidDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/humid/lastNHour")
    public ResponseDTO getLastNHourHumid(@RequestBody LastNHourRequest request) {
        checkLastNHourRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayHumidHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/temp/lastNDay")
    public ResponseDTO getLastNDayTemp(@RequestBody LastNDayRequest request) {
        checkLastNDayRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayTempDaily(request);
        return ResponseDTO.ofSuccess(dto);
    }

    @PostMapping("/temp/lastNHour")
    public ResponseDTO getLastNHourTemp(@RequestBody LastNHourRequest request) {
        checkLastNHourRequest(request);
        NormalCompleteListDTO dto = machineDataServiceImpl.getOneDayTempHourly(request);
        return ResponseDTO.ofSuccess(dto);
    }

    private void checkLastNDayRequest(LastNDayRequest request) {
        if (request == null) {
            throw new ParamErrorException();
        }
        if (StringUtils.isEmpty(request.getUid())) {
            throw new ParamErrorException("empty uid");
        }
        if (request.getLastNDay() < 0) {
            throw new ParamErrorException("lastNDay<0");
        }
        if (!CompleteMethodEnum.isValidCode(request.getCompleteType())) {
            throw new ParamErrorException("completeType error");
        }
        if(!checkUidValid(request.getUid())){
            throw new UnauthorizedException();
        }
    }

    private void checkLastNHourRequest(LastNHourRequest request) {
        if (request == null) {
            throw new ParamErrorException();
        }
        if (StringUtils.isEmpty(request.getUid())) {
            throw new ParamErrorException("empty uid");
        }
        if (!CompleteMethodEnum.isValidCode(request.getCompleteType())) {
            throw new ParamErrorException("completeType error");
        }
        if(!checkUidValid(request.getUid())){
            throw new UnauthorizedException();
        }
    }

    private boolean checkUidValid(String uid) {
        return UserRoleEnum.ADMIN.getName().equals(ShiroUtil.getCurrentRole()) || uid.equals(ShiroUtil.getCurrentUid());
    }
}
