package edu.nju.service;

import edu.nju.asyncTask.PartialCompletionThread;
import edu.nju.asyncTask.V2CompletionThread;
import edu.nju.asyncTask.V3CompletionThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataCompletionImpl implements DataCompletion {

    private static final int pageSize = 10000;  //以10000个数据为一组进行补全

    @Resource
    private UserService userService;

    @Resource
    private PartialCompletionThread partialCompletionThread;
    @Resource
    private V2CompletionThread v2CompletionThread;
    @Resource
    private V3CompletionThread v3CompletionThread;

    @Override
    public void partialCompletion() {
        List<String> allUsers = userService.findAllV2Uids();
        for (String one : allUsers) {
            partialCompletionThread.iterateAndComplete(one, pageSize);
        }
    }

    @Override
    public void partialCompletion(List<String> uidList) {
        for (String oneUid : uidList) {
            partialCompletionThread.iterateAndComplete(oneUid, pageSize);
        }
    }

    @Override
    public void v2Completion() {
        List<String> allUsers = userService.findAllV2Uids();
        for (String one : allUsers) {
            v2CompletionThread.iterateAndComplete(one, pageSize);
        }
    }

    @Override
    public void v2Completion(List<String> uidList) {
        for (String oneUid : uidList) {
            v2CompletionThread.iterateAndComplete(oneUid, pageSize);
        }
    }

    @Override
    public void v3Completion() {
        List<String> allUsers = userService.findAllV3Uids();
        for (String one : allUsers) {
            v3CompletionThread.iterateAndComplete(one, pageSize);
        }
    }

    @Override
    public void v3Completion(List<String> uidList) {
        for (String oneUid : uidList) {
            v3CompletionThread.iterateAndComplete(oneUid, pageSize);
        }
    }
}
