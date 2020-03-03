package edu.nju.controller;

import edn.nju.ResponseDTO;
import edu.nju.model.machine.MachineBasicInfo;
import edu.nju.request.MachineQueryCond;
import edu.nju.service.MachineInfoService;
import edu.nju.service.MachineLatestStatusService;
import edu.nju.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private UserService userService;
    @Resource
    private MachineLatestStatusService machineLatestStatusService;

    @PostMapping("/getList")
    public ResponseDTO getList(@RequestBody MachineQueryCond queryCond) {
        Map<String, List> map = new HashMap<>();
        List<MachineBasicInfo> res = new ArrayList<>();

        int curPage = queryCond.getCurPage();
        int pageSize = queryCond.getPageSize();
        int offset = (curPage - 1) * pageSize;
        Date createTimeGTE = queryCond.getCreateTimeGTE();
        Date createTimeLTE = queryCond.getCreateTimeLTE();
        int overCountGTE = queryCond.getOverCountGTE();
        int overCountLTE = queryCond.getOverCountLTE();
        int isPower = queryCond.getIsPower();
        //检测两个必填项是否合理
        if (curPage < 0 || pageSize <= 0) {
            return ResponseDTO.ofParamError("param error: curPage or pageSize is illegal!");
        }
        //如果有uid的话，其他条件就不看了
        if (!StringUtils.isEmpty(queryCond.getUid())) {
            res.add(machineInfoService.getMachineBasicInfoByUid(userService.findByUid(queryCond.getUid())));
            map.put("machineList", res);
            return ResponseDTO.ofSuccess(map);
        }
        //若果没有uid且必填项合理，按照筛选条件来进行筛选
        if (createTimeGTE != null && createTimeLTE != null &&
                isPower != -1 &&
                overCountGTE > 0 && overCountLTE > 0) {
            res = machineLatestStatusService.findByQueryCond(createTimeGTE, createTimeLTE, isPower,
                    overCountGTE, overCountLTE, offset, pageSize);
        }
        else if (createTimeGTE != null && createTimeLTE != null &&
                isPower != -1 &&
                overCountGTE == 0 && overCountLTE == 0) {
            res = machineLatestStatusService.findByQueryCond(createTimeGTE, createTimeLTE, isPower,
                    offset, pageSize);
        }
        else if (createTimeGTE != null && createTimeLTE != null &&
                isPower == -1 &&
                overCountGTE > 0 && overCountLTE > 0) {
            res = machineLatestStatusService.findByQueryCond(createTimeGTE, createTimeLTE, overCountGTE,
                    overCountLTE, offset, pageSize);
        }
        else if (createTimeGTE == null && createTimeLTE == null &&
                isPower != -1 &&
                overCountGTE > 0 && overCountLTE > 0) {
            res = machineLatestStatusService.findByQueryCond(overCountGTE, overCountLTE, isPower,
                    offset, pageSize);
        }
        else if (createTimeGTE != null && createTimeLTE != null &&
                isPower == -1 &&
                overCountGTE == 0 && overCountLTE == 0) {
            res = machineLatestStatusService.findByQueryCond(createTimeGTE, createTimeLTE,
                    offset, pageSize);
        }
        else if (createTimeGTE == null && createTimeLTE == null &&
                isPower != -1 &&
                overCountGTE == 0 && overCountLTE == 0) {
            res = machineLatestStatusService.findByQueryCond(isPower, offset, pageSize);
        }
        else if (createTimeGTE == null && createTimeLTE == null &&
                isPower == -1 &&
                overCountGTE > 0 && overCountLTE > 0) {
            res = machineLatestStatusService.findByQueryCond(overCountGTE, overCountLTE,
                    offset, pageSize);
        }

        map.put("machineList", res);
        return ResponseDTO.ofSuccess(map);
    }

    @GetMapping("/getUIDInf")
    public ResponseDTO getUidInfo(@RequestParam String uid) {
        return ResponseDTO.ofSuccess(
                machineInfoService.getMachineBasicInfoByUid(userService.findByUid(uid)));
    }
}
