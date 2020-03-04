package edu.nju.controller;

import edu.nju.model.statistic.UserLocation;
import edu.nju.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/4 12:08
 * @description: TODO
 */

@RestController
@RequestMapping("/location")
public class UserLocationController {

    @Resource
    private LocationService locationService;

    @GetMapping("/china/userList")
    public List<UserLocation> nationalUserList() {
        return locationService.getNationalUserLocation();
    }

    @GetMapping("/province/userList")
    public List<UserLocation> provincialUserList(@RequestParam String province) {
        return locationService.getProvincialUserLocation(province);
    }

}
