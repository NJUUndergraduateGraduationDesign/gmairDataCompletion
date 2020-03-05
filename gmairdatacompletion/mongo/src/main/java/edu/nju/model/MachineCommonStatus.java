package edu.nju.model;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/5 15:11
 * @description：
 */

public interface MachineCommonStatus {
    String getUid();

    int getCompleteCode();

    boolean isBlockFlag();

    long getCreateAt();
}
