package edu.nju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 23:52
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NormalCompleteMapDTO {
    private Map<String,Object> normal;
    private Map<String,Object> complete;
}
