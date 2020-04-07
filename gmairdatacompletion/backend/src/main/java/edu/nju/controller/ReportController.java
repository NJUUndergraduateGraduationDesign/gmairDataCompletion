package edu.nju.controller;

import edn.nju.ResponseDTO;
import edu.nju.service.UserReportService;
import edu.nju.shiro.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/7 20:52
 * @description：月报
 */

@Slf4j
@RestController
@RequestMapping("/report")
@RequiresRoles("user")
public class ReportController {
    @Resource
    UserReportService userReportServiceImpl;

    @GetMapping
    public ResponseDTO getUserReport(@RequestParam String uid) {
        if (StringUtils.isEmpty(uid)) {
            return ResponseDTO.ofParamError();
        }
        if (!uid.equals(ShiroUtil.getCurrentUid())) {
            return ResponseDTO.ofUnauthorizedError();
        }
        return ResponseDTO.ofSuccess(userReportServiceImpl.getMonthlyReport(uid));
    }
}
