package edu.nju.service;

import java.util.List;

public interface DataCompletion {

    void partialCompletion();

    void partialCompletion(List<String> uidList);

    void v2Completion();

    void v2Completion(List<String> uidList);

    void v3Completion();

    void v3Completion(List<String> uidList);

}
