package edu.nju.service.impl;

import edu.nju.bo.MachineV2StatusDaily;
import edu.nju.bo.MachineV2StatusHourly;
import edu.nju.bo.MachineV3StatusDaily;
import edu.nju.bo.MachineV3StatusHourly;
import edu.nju.dao.status.*;
import edu.nju.model.status.*;
import edu.nju.service.MachineStatusDBService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 17:12
 * @description：save MachineStatusHourly/Daily business object to mysql db
 */

@Transactional
@Service
public class MachineStatusDBServiceImpl implements MachineStatusDBService {
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
    @Resource
    Co2DailyDao co2DailyDaoImpl;
    @Resource
    HeatDailyDao heatDailyDaoImpl;
    @Resource
    HumidDailyDao humidDailyDaoImpl;
    @Resource
    IndoorPm25DailyDao indoorPm25DailyDaoImpl;
    @Resource
    InnerPm25DailyDao innerPm25DailyDaoImpl;
    @Resource
    ModeDailyDao modeDailyDaoImpl;
    @Resource
    PowerDailyDao powerDailyDaoImpl;
    @Resource
    TempDailyDao tempDailyDaoImpl;
    @Resource
    VolumeDailyDao volumeDailyDaoImpl;
    
    @Override
    public void saveMachineV2StatusHourly(MachineV2StatusHourly status) {
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
    
    @Override
    public void saveMachineV2StatusDaily(MachineV2StatusDaily status) {
        Co2Daily co2Daily = new Co2Daily();
        BeanUtils.copyProperties(status, co2Daily);
        co2DailyDaoImpl.add(co2Daily);

        HeatDaily heatDaily = new HeatDaily();
        BeanUtils.copyProperties(status, heatDaily);
        heatDailyDaoImpl.add(heatDaily);

        HumidDaily humidDaily = new HumidDaily();
        BeanUtils.copyProperties(status, humidDaily);
        humidDailyDaoImpl.add(humidDaily);

        IndoorPm25Daily indoorPm25Daily = new IndoorPm25Daily();
        BeanUtils.copyProperties(status, indoorPm25Daily);
        indoorPm25Daily.setAveragePm25(status.getAveragePm25());
        indoorPm25DailyDaoImpl.add(indoorPm25Daily);

        ModeDaily modeDaily = new ModeDaily();
        BeanUtils.copyProperties(status, modeDaily);
        modeDailyDaoImpl.add(modeDaily);

        PowerDaily powerDaily = new PowerDaily();
        BeanUtils.copyProperties(status, powerDaily);
        powerDailyDaoImpl.add(powerDaily);

        TempDaily tempDaily = new TempDaily();
        BeanUtils.copyProperties(status, tempDaily);
        tempDailyDaoImpl.add(tempDaily);

        VolumeDaily volumeDaily = new VolumeDaily();
        BeanUtils.copyProperties(status, volumeDaily);
        volumeDailyDaoImpl.add(volumeDaily);
    }

    @Override
    public void saveMachineV3StatusHourly(MachineV3StatusHourly status) {
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
        indoorPm25Hourly.setAveragePm25(status.getAverageIndoorPm25());
        indoorPm25HourlyDaoImpl.add(indoorPm25Hourly);

        InnerPm25Hourly innerPm25Hourly = new InnerPm25Hourly();
        BeanUtils.copyProperties(status, innerPm25Hourly);
        innerPm25Hourly.setAveragePm25(status.getAverageInnerPm25());
        innerPm25HourlyDaoImpl.add(innerPm25Hourly);

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

    @Override
    public void saveMachineV3StatusDaily(MachineV3StatusDaily status) {
        Co2Daily co2Daily = new Co2Daily();
        BeanUtils.copyProperties(status, co2Daily);
        co2DailyDaoImpl.add(co2Daily);

        HeatDaily heatDaily = new HeatDaily();
        BeanUtils.copyProperties(status, heatDaily);
        heatDailyDaoImpl.add(heatDaily);

        HumidDaily humidDaily = new HumidDaily();
        BeanUtils.copyProperties(status, humidDaily);
        humidDailyDaoImpl.add(humidDaily);

        IndoorPm25Daily indoorPm25Daily = new IndoorPm25Daily();
        BeanUtils.copyProperties(status, indoorPm25Daily);
        indoorPm25Daily.setAveragePm25(status.getAverageIndoorPm25());
        indoorPm25DailyDaoImpl.add(indoorPm25Daily);

        InnerPm25Daily innerPm25Daily = new InnerPm25Daily();
        BeanUtils.copyProperties(status, innerPm25Daily);
        innerPm25Daily.setAveragePm25(status.getAverageInnerPm25());
        innerPm25DailyDaoImpl.add(innerPm25Daily);

        ModeDaily modeDaily = new ModeDaily();
        BeanUtils.copyProperties(status, modeDaily);
        modeDailyDaoImpl.add(modeDaily);

        PowerDaily powerDaily = new PowerDaily();
        BeanUtils.copyProperties(status, powerDaily);
        powerDailyDaoImpl.add(powerDaily);

        TempDaily tempDaily = new TempDaily();
        BeanUtils.copyProperties(status, tempDaily);
        tempDailyDaoImpl.add(tempDaily);

        VolumeDaily volumeDaily = new VolumeDaily();
        BeanUtils.copyProperties(status, volumeDaily);
        volumeDailyDaoImpl.add(volumeDaily);
    }

    @Override
    public void saveMachinePartialStatusHourly(InnerPm25Hourly status) {
        innerPm25HourlyDaoImpl.add(status);
    }

    @Override
    public void saveMachinePartialStatusDaily(InnerPm25Daily status) {
        innerPm25DailyDaoImpl.add(status);
    }
}
