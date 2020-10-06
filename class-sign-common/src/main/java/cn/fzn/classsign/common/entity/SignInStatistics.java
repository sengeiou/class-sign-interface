package cn.fzn.classsign.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SignInStatistics {
    private Integer ssid;
    private Integer cid;
    private String name;
    private Long startTime;
    private Integer checkInNum;
    private Integer total;
    private String status;
}
