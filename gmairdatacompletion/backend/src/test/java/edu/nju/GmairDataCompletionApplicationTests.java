package edu.nju;

import edu.nju.model.MachineV2Status;
import edu.nju.service.MachineV2StatusService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GmairDataCompletionApplication.class)
class GmairDataCompletionApplicationTests {

    @Resource
    MachineV2StatusService machineV2StatusServiceImpl;

    @Test
    void contextLoads() {
    }

    @Test
    void testFindById() {
        MachineV2Status machineV2Status = machineV2StatusServiceImpl.findById("5d9249a980a5f93e1e393d48");
        System.out.println(machineV2Status);
    }

    @Test
    void testFindByUID() {
        List<MachineV2Status> machineV2StatusList = machineV2StatusServiceImpl.findByUid("F0FE6BC350A0");
        System.out.println(machineV2StatusList.size());
    }

    @Test
    void testFetchBatch() {
        System.out.println("startTime:"+new Date());
        List<MachineV2Status> machineV2StatusList = machineV2StatusServiceImpl.fetchBatchByUid("F0FE6BC350A0", 1569859200000L, 1569945600000L);
        System.out.println("endTime:"+new Date());
        System.out.println(machineV2StatusList.size());
        System.out.println(machineV2StatusList);
    }

    @Test
    void testGetStartTimeByUid(){
        long startTime=machineV2StatusServiceImpl.getStartTimeByUid("F0FE6BC350A0");
        System.out.println("startTime:"+startTime);
    }

    @Test
    void testGetAllUids(){
        List<String> uidList=machineV2StatusServiceImpl.getAllUids();
        System.out.println("uidList:"+uidList);
    }

}
