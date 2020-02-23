package edu.nju.controller;

import edu.nju.model.MachineV2Status;
import edu.nju.service.MachineV2StatusService;
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
    MachineV2StatusService machineV2StatusService;

    @PostMapping(value = "/status")
    public MachineV2Status status(@RequestParam String id) {
        return machineV2StatusService.findById(id);
    }

}
