package edu.nju.controller;

import edn.nju.ResponseDTO;
import edu.nju.dto.MachineBasicInfo;
import edu.nju.dto.MachineQueryCond;
import edu.nju.service.MachineInfoService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/26 14:34
 * @description： controller of machine
 */

@RestController
@RequestMapping("/machine")
public class MachineController {

    @Resource
    private MachineInfoService machineInfoService;

    @PostMapping("/getList")
    public ResponseDTO getList(@RequestBody MachineQueryCond queryCond) {
        //检测两个必填项是否合理
        if (queryCond.getCurPage() < 0 || queryCond.getPageSize() <= 0) {
            return ResponseDTO.ofParamError("param error: curPage or pageSize is illegal!");
        }
        //如果有uid的话，其他条件就不看了
        if (!StringUtils.isEmpty(queryCond.getUid())) {
            return ResponseDTO.ofSuccess(machineInfoService.getMachineBasicInfoByUid(queryCond.getUid()));
        }
        //若果没有uid且必填项合理，按照筛选条件来进行筛选

        return ResponseDTO.ofSuccess(new MachineBasicInfo());
    }

    @PostMapping("/getUidInfo")
    public ResponseDTO getUidInfo(@RequestParam String uid) {
        return ResponseDTO.ofSuccess(machineInfoService.getMachineBasicInfoByUid(uid));
    }
}
