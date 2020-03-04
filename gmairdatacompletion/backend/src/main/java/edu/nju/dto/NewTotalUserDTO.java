package edu.nju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 16:30
 * @description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewTotalUserDTO {
    private int totalUser;
    private int newUser;
}
