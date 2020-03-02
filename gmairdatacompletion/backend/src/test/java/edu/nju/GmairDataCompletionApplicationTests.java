package edu.nju;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import edn.nju.enums.MachineStatusTypeEnum;
import edu.nju.controller.MachineController;
import edu.nju.dto.MachineQueryCond;
import edu.nju.model.MachineV2Status;
import edu.nju.model.User;
import edu.nju.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GmairDataCompletionApplication.class)
class GmairDataCompletionApplicationTests {

    @Resource
    MachineV2StatusService machineV2StatusServiceImpl;
    @Resource
    MachineV3StatusService machineV3StatusServiceImpl;
    @Resource
    MachinePartialStatusService machinePartialStatusServiceImpl;
    @Resource
    DataCompletion dataCompletion;
    @Resource
    UserService userService;
    @Resource
    MachineStatusService machineStatusServiceImpl;
    @Resource
    MachineInfoService machineInfoService;
    @Resource
    MachineController machineController;

    @Test
    void contextLoads() {
    }

    @Test
    void testLogs() {
        log.info("testLog");
    }

    @Test
    void testFindById() {
        MachineV2Status machineV2Status = machineV2StatusServiceImpl.findById("5d9249a980a5f93e1e393d48");
        System.out.println(machineV2Status);
    }


    @Test
    void testFetchBatch1() {
        System.out.println("startTime:" + new Date());
        Page<MachineV2Status> machineV2StatusPage = machineV2StatusServiceImpl.fetchBatchByUid("F0FE6BC350A0", 0, 50);
        System.out.println("endTime:" + new Date());
        System.out.println(machineV2StatusPage.getSize());
        System.out.println(machineV2StatusPage.getContent());
    }

    @Test
    void testFetchBatch2(){
        System.out.println("startTime:" + new Date());
        List<MachineV2Status> machineV2StatusList = machineV2StatusServiceImpl.fetchBatchByUid("F0FE6BC350A0", 1569780000000L, 1569945600000L);
        System.out.println("endTime:" + new Date());
        System.out.println(machineV2StatusList.get(0));
    }

    @Test
    void testGetStartTimeByUid() {
        long startTime = machineV2StatusServiceImpl.getStartTimeByUid("F0FE6BC350A0");
        System.out.println("startTime:" + startTime);
    }

    @Test
    void testGetAllUids() {
        List<String> v2UidList = machineV2StatusServiceImpl.getAllUids();
        List<String> v3UidList = machineV3StatusServiceImpl.getAllUids();
        List<String> partialUidList = machinePartialStatusServiceImpl.getAllUids();
        System.out.println("v2UidList:" + v2UidList.size());
        System.out.println("v3UidList:" + v3UidList.size());
        System.out.println("partialUidList:" + partialUidList.size());
        Set<String> v2v3UidSet = Sets.intersection(Sets.newHashSet(v2UidList), Sets.newHashSet(v3UidList));
        Set<String> v2partialUidSet = Sets.intersection(Sets.newHashSet(v2UidList), Sets.newHashSet(partialUidList));
        Set<String> v3partialUidSet = Sets.intersection(Sets.newHashSet(v3UidList), Sets.newHashSet(partialUidList));
        Set<String> v2v3partialUidSet = Sets.intersection(v2v3UidSet, Sets.newHashSet(partialUidList));
        System.out.println("v2v3:" + v2v3UidSet.size());
        System.out.println("v2partial:" + v2partialUidSet.size());
        System.out.println("v3partial:" + v3partialUidSet.size());
        System.out.println("v2v3partial:" + v2v3partialUidSet.size());
    }

    @Test
    void testInsert() {
        Page<MachineV2Status> machineV2StatusPage = machineV2StatusServiceImpl.fetchBatchByUid("F0FE6BC350A0", 0, 1);
        MachineV2Status machineV2Status = machineV2StatusPage.getContent().get(0);
        Date time = new Date(machineV2Status.getCreateAt() + 100000);
        machineV2Status.setCreateAt(time.getTime());
        log.info("toCreate:" + machineV2Status);
        machineV2StatusServiceImpl.insertBatch(Lists.newArrayList(machineV2Status));
    }

    @Test
    void testDataCompletion() {
        System.out.println("start time: " + new Date());
        dataCompletion.v2Completion();
        System.out.println("end time: " + new Date());
        try{
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void changeTableUser() {
        List<String> allV2Uids = machineV2StatusServiceImpl.getAllUids();
        int count = 0;
        for (String oneId : allV2Uids) {
            User user = userService.findByUid(oneId);
            if (user == null) {
                count++;
                System.out.println("V2: " + oneId);
                continue;
            }
            user.setDataType(MachineStatusTypeEnum.MACHINE_V2_STATUS.getCode());
            userService.update(user);
        }

        List<String> allV3Uids = machineV3StatusServiceImpl.getAllUids();
        for (String oneId : allV3Uids) {
            User user = userService.findByUid(oneId);
            if (user == null) {
                count++;
                System.out.println("V3: " + oneId);
                continue;
            }
            user.setDataType(MachineStatusTypeEnum.MACHINE_V3_STATUS.getCode());
            userService.update(user);
        }
        System.out.println("total: " + count);
    }

    @Test
    void testAnalyze(){
        machineStatusServiceImpl.handleAllData();
    }

    @Test
    void testMethods() {
        System.out.println(machineController.getUidInfo("F0FE6BAA601B").getData());
    }
}
