package edu.nju.service.impl;

import edu.nju.bo.MachineStatusHourly;
import edu.nju.dao.*;
import edu.nju.model.status.*;
import edu.nju.service.MachineStatusHourlyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 17:12
 * @description：save MachineStatusHourly business object to mysql db
 */

@Service
public class MachineStatusHourlyServiceImpl implements MachineStatusHourlyService {
    @Resource
    Co2HourlyDao co2HourlyDaoImpl;
    @Resource
    HeatHourlyDao heatHourlyDaoImpl;
    @Resource
    HumidHourlyDao humidHourlyDaoImpl;
    @Resource
    IndoorPm25HourlyDao indoorPm25HourlyDaoImpl;
    @Resource
    InnerPm25HourlyDao innerPm25HourlyDaoImpl;
    @Resource
    ModeHourlyDao modeHourlyDaoImpl;
    @Resource
    PowerHourlyDao powerHourlyDaoImpl;
    @Resource
    TempHourlyDao tempHourlyDaoImpl;
    @Resource
    VolumeHourlyDao volumeHourlyDaoImpl;

    public void saveMachineStatusHourlyList(List<MachineStatusHourly> list) {
        for (MachineStatusHourly status : list) {
            saveMachineStatusHourly(status);
        }
    }

    public void saveMachineStatusHourly(MachineStatusHourly status) {
        Co2Hourly co2Hourly = new Co2Hourly();
        BeanUtils.copyProperties(status, co2Hourly);
        co2HourlyDaoImpl.add(co2Hourly);

        HeatHourly heatHourly = new HeatHourly();
        BeanUtils.copyProperties(status, heatHourly);
        heatHourlyDaoImpl.add(heatHourly);

        HumidHourly humidHourly = new HumidHourly();
        BeanUtils.copyProperties(status, humidHourly);
        humidHourlyDaoImpl.add(humidHourly);

        IndoorPm25Hourly indoorPm25Hourly = new IndoorPm25Hourly();
        BeanUtils.copyProperties(status, indoorPm25Hourly);
        indoorPm25Hourly.setAveragePm25(status.getAveragePm25());
        indoorPm25HourlyDaoImpl.add(indoorPm25Hourly);

        ModeHourly modeHourly = new ModeHourly();
        BeanUtils.copyProperties(status, modeHourly);
        modeHourlyDaoImpl.add(modeHourly);

        PowerHourly powerHourly = new PowerHourly();
        BeanUtils.copyProperties(status, powerHourly);
        powerHourlyDaoImpl.add(powerHourly);

        TempHourly tempHourly = new TempHourly();
        BeanUtils.copyProperties(status, tempHourly);
        tempHourlyDaoImpl.add(tempHourly);

        VolumeHourly volumeHourly = new VolumeHourly();
        BeanUtils.copyProperties(status, volumeHourly);
        volumeHourlyDaoImpl.add(volumeHourly);
    }
}
