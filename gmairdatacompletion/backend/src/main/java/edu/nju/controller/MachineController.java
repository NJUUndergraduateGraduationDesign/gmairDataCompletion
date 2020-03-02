package edu.nju.controller;

import edn.nju.ResponseDTO;
import edu.nju.dto.MachineBasicInfo;
import edu.nju.dto.MachineQueryCond;
import edu.nju.model.User;
import edu.nju.service.MachineInfoService;
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

    @PostMapping("/getList")
    public ResponseDTO getList(@RequestBody MachineQueryCond queryCond) {
        Map<String, List> map = new HashMap<>();
        List<MachineBasicInfo> res = new ArrayList<>();

        int curPage = queryCond.getCurPage();
        int pageSize = queryCond.getPageSize();
        int offset = (curPage - 1) * pageSize;
        Date createTimeGTE = queryCond.getCreateTimeGTE();
        Date createTimeLTE = queryCond.getCreateTimeLTE();
        //检测两个必填项是否合理
        if (curPage < 0 || pageSize <= 0) {
            return ResponseDTO.ofParamError("param error: curPage or pageSize is illegal!");
        }
        //如果有uid的话，其他条件就不看了
        if (!StringUtils.isEmpty(queryCond.getUid())) {
            res.add(machineInfoService.getMachineBasicInfoByUid(queryCond.getUid()));
            map.put("machineList", res);
            return ResponseDTO.ofSuccess(map);
        }
        //若果没有uid且必填项合理，按照筛选条件来进行筛选
        //根据createTimeGTE, createTimeLTE筛选
        List<User> tmpStore;
        if (createTimeGTE != null && createTimeLTE != null) {
            tmpStore = userService.findByQueryCond(createTimeGTE, createTimeLTE);
        }
        else {
            tmpStore = userService.findAllUsers();
        }
        //TODO 根据pm2.5超过25的天数来判断
        //根据isPower判断，然后按照offset和pageSize添加进res
        int meetCount = 0;
        for (User oneUser : tmpStore) {
            MachineBasicInfo oneBasicInfo = machineInfoService.getMachineBasicInfoByUid(oneUser.getUid());
            if (queryCond.getIsPower() == -1 || queryCond.getIsPower() == oneBasicInfo.getIsPower()) {
                meetCount++;
                if (meetCount > offset && (meetCount - offset) <= pageSize) {
                    res.add(oneBasicInfo);
                }
            }
        }

        map.put("machineList", res);
        return ResponseDTO.ofSuccess(map);
    }

    @GetMapping("/getUIDInf")
    public ResponseDTO getUidInfo(@RequestParam String uid) {
        return ResponseDTO.ofSuccess(machineInfoService.getMachineBasicInfoByUid(uid));
    }
}
