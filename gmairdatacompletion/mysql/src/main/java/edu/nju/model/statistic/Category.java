package edu.nju.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ：CK
 * @date ：Created in 2020/3/10 11:23
 * @description：
 */

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    @Id
    private String uid;

    private int environment;

    private int effect;
}
