package edu.nju.model.status;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 22:55
 * @description：
 */

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InnerPm25Hourly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uid;

    private double averagePm25;

    private long createAt;
    private Integer completeMethod;

    public Map<String, Object> toDTOMap() {
        return ImmutableMap.of("createTime", createAt, "averagePm25", averagePm25);
    }

    public InnerPm25Hourly(String uid, long createAt, Integer completeMethod, double averagePm25) {
        this.uid = uid;
        this.createAt = createAt;
        this.completeMethod = completeMethod;
        this.averagePm25 = averagePm25;
    }
}
