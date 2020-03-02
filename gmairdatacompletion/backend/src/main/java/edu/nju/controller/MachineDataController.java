package edu.nju.controller;

import edn.nju.ResponseDTO;
import edn.nju.enums.CompleteMethodEnum;
import edu.nju.model.status.Co2Daily;
import edu.nju.request.LastNDayRequest;
import edu.nju.service.MachineDataService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping("/pm25/lastNday")
    public ResponseDTO getLastNDayPm25(@RequestBody LastNDayRequest request){
        if(request==null|| StringUtils.isEmpty(request.getUid())||
                request.getLastNDay()<=0|| !CompleteMethodEnum.isValidCode(request.getCompleteType())){
            return ResponseDTO.ofParamError();
        }
        List<Co2Daily> dailyList=machineDataServiceImpl.getLastNDayCo2Daily(request);
        return ResponseDTO.ofSuccess(null);
    }
}
