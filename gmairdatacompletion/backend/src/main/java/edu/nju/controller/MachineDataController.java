package edu.nju.controller;

import edn.nju.ResponseDTO;
import edn.nju.enums.CompleteMethodEnum;
import edu.nju.dto.NormalCompleteListDTO;
import edu.nju.request.LastNDayRequest;
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
        if (request == null || StringUtils.isEmpty(request.getUid()) ||
                request.getLastNDay() <= 0 || !CompleteMethodEnum.isValidCode(request.getCompleteType())) {
            return ResponseDTO.ofParamError();
        }
        NormalCompleteListDTO dto = machineDataServiceImpl.getLastNDayCo2Daily(request);
        return ResponseDTO.ofSuccess(dto);
    }
}
