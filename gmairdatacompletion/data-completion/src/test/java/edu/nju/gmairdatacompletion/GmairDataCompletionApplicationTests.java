package edu.nju.gmairdatacompletion;

import edu.nju.mongo.model.MachineV2Status;
import edu.nju.mongo.service.MachineV2StatusService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
    void testCount(){
        System.out.println(machineV2StatusServiceImpl.count());
    }

    @Test
    void testFindById(){
        MachineV2Status machineV2Status = machineV2StatusServiceImpl.findById("5d9249a980a5f93e1e393d48");
        System.out.println(machineV2Status);
    }

    @Test
    void testFindByUID(){
        List<MachineV2Status> machineV2StatusList = machineV2StatusServiceImpl.findByUid("F0FE6BC350A0");
        System.out.println(machineV2StatusList.size());
    }

}
