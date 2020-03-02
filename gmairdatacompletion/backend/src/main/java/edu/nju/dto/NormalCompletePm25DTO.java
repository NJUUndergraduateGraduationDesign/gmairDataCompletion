package edu.nju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 23:56
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NormalCompletePm25DTO {
    List<Map<String,Object>> indoorPm25;
    List<Map<String,Object>> innerPm25;
    List<Map<String,Object>> comIndoorPm25;
    List<Map<String,Object>> comInnerPm25;
}
