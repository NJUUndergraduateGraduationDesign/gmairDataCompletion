package edu.nju.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/2 22:57
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastNDayRequest {
    String uid;
    int lastNDay;
    int completeType;
}
