package edu.nju;

import edu.nju.mongo.model.MachineStatus;
import edu.nju.mongo.service.MachineStatusService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GmairDataCompletionApplication.class)
class GmairDataCompletionApplicationTest {

    @Resource
    MachineStatusService machineStatusServiceImpl;

    @Test
    void contextLoads() {
    }

    @Test
    void findById(){
        List<MachineStatus> machineStatus=machineStatusServiceImpl.findByUid("F0FE6BC350A0");
        System.out.println(machineStatus);
    }

    @Test
    void testCount(){
        System.out.println(machineStatusServiceImpl.count());
    }
}
