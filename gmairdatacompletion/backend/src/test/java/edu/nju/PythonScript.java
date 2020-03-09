package edu.nju;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import edu.nju.model.MachineAvgDailyData;
import edu.nju.service.*;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by CK.
 * User: CK
 * Date: 2020/3/7
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GmairDataCompletionApplication.class)
public class PythonScript {

    @Resource
    MachineDataRadarService machineDataRadarService;

    @Test
    public void testPython() {
        //List<MachineAvgDailyData> dailyDataList=machineDataRadarService.getAvgDailyData(Constant.MachineData.BEST_METHOD,Constant.MachineData.LAST_MONTH);
        List<MachineAvgDailyData> dailyDataList=new ArrayList<>();
        dailyDataList.add(new MachineAvgDailyData("ff",1,1,1));
        dailyDataList.add(new MachineAvgDailyData("ff",11,11,11));
        try {
            String[] args = new String[] { "E:\\anaconda\\python.exe", "E:\\test.py", JSON.toJSONString(dailyDataList, SerializerFeature.WriteNonStringValueAsString).toString()};
            System.err.println(JSON.toJSONString(dailyDataList, SerializerFeature.WriteNonStringValueAsString));
            Process pr = Runtime.getRuntime().exec(
                   args);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
            } catch (Exception e) {
                 e.printStackTrace();
            }
    }
}
