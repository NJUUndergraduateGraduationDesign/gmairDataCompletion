package edu.nju.model.status;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
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
public class HeatDaily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uid;

    private int heatOnMinute;
    private int heatOffMinute;

    private long createAt;
    private Integer completeMethod;

    public Map<String, Object> toDTOMap() {
        return ImmutableMap.of("createTime", createAt, "heatOnMinute", heatOnMinute);
    }
}
