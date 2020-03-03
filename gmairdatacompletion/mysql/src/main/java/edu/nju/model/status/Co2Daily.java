package edu.nju.model.status;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 23:06
 * @description：
 */

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Co2Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uid;

    private double averageCo2;

    private long createAt;
    private Integer completeMethod;

    public Map<String, Object> toDTOMap() {
        return ImmutableMap.of("createTime", createAt, "averageCo2", averageCo2);
    }
}

