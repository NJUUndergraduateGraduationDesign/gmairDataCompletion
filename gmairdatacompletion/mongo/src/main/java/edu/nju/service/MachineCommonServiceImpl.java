package edu.nju.service;

import com.google.common.collect.Lists;
import edu.nju.model.MachineCommonStatus;
import edu.nju.model.MachineV2Status;
import edu.nju.repository.MachineCommonStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/5 14:54
 * @description：
 */

public class MachineCommonServiceImpl<T extends MachineCommonStatus> implements MachineCommonService<T> {
    @Resource
    MongoTemplate mongoTemplate;

    protected MachineCommonStatusRepository<T> repository;

    protected void setRepository(MachineCommonStatusRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public void create(T t) {
        repository.insert(t);
    }

    @Override
    public void saveOrUpdate(T t) {
        repository.save(t);
    }

    @Override
    public List<String> getAllUids() {
        return mongoTemplate.query(MachineV2Status.class).distinct("uid").as(String.class).all();
    }

    @Override
    public long getStartTimeByUid(String uid) {
        T t = repository.findFirstByUidOrderByCreateAt(uid);
        return t == null ? 0 : t.getCreateAt();
    }

    @Override
    public long getLatestTimeByUid(String uid) {
        T t = repository.findFirstByUidOrderByCreateAtDesc(uid);
        return t == null ? 0 : t.getCreateAt();
    }

    @Override
    public T getLatestRecord(String uid) {
        return repository.findFirstByUidOrderByCreateAtDesc(uid);
    }

    @Override
    public List<T> fetchBatchByUid(String uid, long start, long end) {
        return repository.findByUid(uid, start, end);
    }

    @Override
    public Page<T> fetchBatchByUid(String uid, int pageIndex, int pageSize) {
        return repository.findByUid(uid, PageRequest.of(pageIndex, pageSize, Sort.by("createAt").ascending()));
    }

    @Override
    public List<T> fetchBatchByUid(String uid, long startTime, long endTime, long timeInterval, long timeBias) {
        List<T> res = Lists.newArrayList();
        for (long cur = startTime; cur <= endTime; cur = cur + timeInterval) {
            long left = Math.max(startTime, cur - timeBias);
            long right = Math.min(endTime, cur + timeBias);
            List<T> subList = repository.findByUid(uid, left, right);
            res.addAll(subList);
        }
        return res;
    }

    @Override
    public void insertBatch(List<T> list) {
        repository.insert(list);
    }
}
