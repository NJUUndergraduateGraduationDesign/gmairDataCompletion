package edu.nju.controller;

import edn.nju.ResponseDTO;
import edu.nju.service.LocationService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Bright Chan
 * @date: 2020/3/4 12:08
 * @description: TODO
 */

@RestController
@RequestMapping("/location")
@RequiresRoles("admin")
public class UserLocationController {

    @Resource
    private LocationService locationService;

    @GetMapping("/china/userList")
    public ResponseDTO nationalUserList() {
        return ResponseDTO.ofSuccess(locationService.getNationalUserLocation());
    }

    @GetMapping("/province/userList")
    public ResponseDTO provincialUserList(@RequestParam String province) {
        return ResponseDTO.ofSuccess(locationService.getProvincialUserLocation(province));
    }

}
