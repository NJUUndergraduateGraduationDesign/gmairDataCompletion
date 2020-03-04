package edu.nju.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Bright Chan
 * @date: 2020/3/4 12:08
 * @description: TODO
 */

@RestController
@RequestMapping("/statistic/admin")
public class AdminStatisticController {

    @GetMapping("/china/total")
    public void getNationalTotalAndNewUsers() {

    }

    @GetMapping("/province/total")
    public void getProvincialTotalAndNewUsers(@RequestParam String province) {

    }

    @GetMapping("/china/newNumberPerMonth")
    public void getNationalNumberPerMonthLastYear() {

    }

    @GetMapping("/province/newNumberPerMonth")
    public void getProvincialNumberPerMonthLastYear(@RequestParam String province) {

    }

    @GetMapping("/china/categoryEnvironment")
    public void getNationalCategoryEnvironment() {

    }

    @GetMapping("/province/categoryEnvironment")
    public void getProvincialCategoryEnvironment(@RequestParam String province) {

    }

    @GetMapping("/china/categoryEffect")
    public void getNationalCategoryEffect() {

    }

    @GetMapping("/province/categoryEffect")
    public void getProvincialCategoryEffect(@RequestParam String province) {

    }
}
