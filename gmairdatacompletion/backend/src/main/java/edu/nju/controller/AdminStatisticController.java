package edu.nju.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import edn.nju.ResponseDTO;
import edn.nju.constant.Constant;
import edn.nju.util.TimeUtil;
import edu.nju.dto.NewTotalUserDTO;
import edu.nju.model.statistic.Category;
import edu.nju.model.statistic.CategoryNumber;
import edu.nju.service.CategoryService;
import edu.nju.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Bright Chan
 * @date: 2020/3/4 12:08
 * @description: TODO
 */

@Slf4j
@RestController
@RequestMapping("/statistic/admin")
@RequiresRoles("admin")
public class AdminStatisticController {
    @Resource
    UserService userServiceImpl;

    @Resource
    CategoryService categoryService;

    private static final int MONTH_PER_YEAR = 12;

    @GetMapping("/china/total")
    public ResponseDTO getNationalTotalAndNewUsers() {
        int totalCount = userServiceImpl.count();
        Date endTime = Constant.User.LATEST_USER_BIND_TIME;
        Date startTime = TimeUtil.getStartOfThisMonth(endTime);
        int newCount = userServiceImpl.countByBindTime(startTime, endTime);
        NewTotalUserDTO dto = new NewTotalUserDTO(totalCount, newCount);
        return ResponseDTO.ofSuccess(dto);
    }

    @GetMapping("/province/total")
    public ResponseDTO getProvincialTotalAndNewUsers(@RequestParam String province) {
        int totalCount = userServiceImpl.countByProvince(province);
        Date endTime = Constant.User.LATEST_USER_BIND_TIME;
        Date startTime = TimeUtil.getStartOfThisMonth(endTime);
        int newCount = userServiceImpl.countByProvinceAndBindTime(province, startTime, endTime);
        NewTotalUserDTO dto = new NewTotalUserDTO(totalCount, newCount);
        return ResponseDTO.ofSuccess(dto);
    }

    @GetMapping("/china/newNumberPerMonth")
    public ResponseDTO getNationalNumberPerMonthLastYear() {
        List<Map<String, Object>> res = Lists.newArrayList();
        Date endTime = Constant.User.LATEST_USER_BIND_TIME;
        Date start = TimeUtil.getStartOfThisMonth(endTime);
        for (int i = 0; i < MONTH_PER_YEAR; i++) {
            Date end = TimeUtil.getEndOfThisMonth(start);
            int count = userServiceImpl.countByBindTime(start, end);
            res.add(ImmutableMap.of("createTime", TimeUtil.dateToStrMonth(start), "number", count));
            start = TimeUtil.getNMonthBefore(start, 1);
        }
        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/province/newNumberPerMonth")
    public ResponseDTO getProvincialNumberPerMonthLastYear(@RequestParam String province) {
        List<Map<String, Object>> res = Lists.newArrayList();
        Date endTime = Constant.User.LATEST_USER_BIND_TIME;
        Date start = TimeUtil.getStartOfThisMonth(endTime);
        for (int i = 0; i < MONTH_PER_YEAR; i++) {
            Date end = TimeUtil.getEndOfThisMonth(start);
            int count = userServiceImpl.countByProvinceAndBindTime(province, start, end);
            res.add(ImmutableMap.of("createTime", TimeUtil.dateToStrMonth(start), "number", count));
            start = TimeUtil.getNMonthBefore(start, 1);
        }
        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/china/categoryEnvironment")
    public ResponseDTO getNationalCategoryEnvironment() {
        Map<Integer,Long> res=new HashMap<>();
        List<CategoryNumber> result=categoryService.getAllCategoryEnvironment("china");
        for (CategoryNumber c:result){
            res.put(c.getType(),c.getNumber());
        }
        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/province/categoryEnvironment")
    public ResponseDTO getProvincialCategoryEnvironment(@RequestParam String province) {
        Map<Integer,Long> res=new HashMap<>();
        List<CategoryNumber> result=categoryService.getAllCategoryEnvironment(province);
        for (CategoryNumber c:result){
            res.put(c.getType(),c.getNumber());
        }
        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/china/categoryEffect")
    public ResponseDTO getNationalCategoryEffect() {
        Map<Integer,Long> res=new HashMap<>();
        List<CategoryNumber> result=categoryService.getAllCategoryEffect("china");
        for (CategoryNumber c:result){
            res.put(c.getType(),c.getNumber());
        }
        return ResponseDTO.ofSuccess(res);
    }

    @GetMapping("/province/categoryEffect")
    public ResponseDTO getProvincialCategoryEffect(@RequestParam String province) {
        Map<Integer,Long> res=new HashMap<>();
        List<CategoryNumber> result=categoryService.getAllCategoryEffect(province);
        for (CategoryNumber c:result){
            res.put(c.getType(),c.getNumber());
        }
        return ResponseDTO.ofSuccess(res);
    }
}
