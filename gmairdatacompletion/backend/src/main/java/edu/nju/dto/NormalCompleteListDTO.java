package edu.nju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 23:50
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NormalCompleteListDTO {
    private List<Map<String,Object>> normal;
    private List<Map<String,Object>> complete;
}
