package edu.nju.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 17:08
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastNHourRequest {
    String uid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;
    int completeType;
}
