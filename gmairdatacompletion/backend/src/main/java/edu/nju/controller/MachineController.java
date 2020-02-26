package edu.nju.controller;

import edn.nju.ResponseDTO;
import edu.nju.dto.MachineBasicInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/26 14:34
 * @description：controller of machine
 */

@RestController
@RequestMapping("/machine")
public class MachineController {
    @PostMapping("/getList")
    public ResponseDTO getList(){
        return ResponseDTO.ofSuccess(new MachineBasicInfo());
    }

    @PostMapping("/getUidInf")
    public ResponseDTO getUidInfo(@RequestParam String uid){
        return ResponseDTO.ofSuccess(new MachineBasicInfo());
    }
}
