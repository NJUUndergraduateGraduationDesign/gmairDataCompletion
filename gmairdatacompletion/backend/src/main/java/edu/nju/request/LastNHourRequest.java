package edu.nju.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String uid;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private int completeType;
}
