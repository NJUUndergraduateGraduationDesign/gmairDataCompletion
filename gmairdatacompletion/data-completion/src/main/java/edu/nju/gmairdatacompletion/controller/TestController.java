package edu.nju.gmairdatacompletion.controller;

import edu.nju.mongo.model.MachineStatus;
import edu.nju.mongo.service.MachineStatusService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/21 18:09
 * @description：
 */

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    MachineStatusService machineStatusService;

    @PostMapping(value = "/status")
    public MachineStatus status(@RequestParam String id) {
        return machineStatusService.findById(id);
    }

}
