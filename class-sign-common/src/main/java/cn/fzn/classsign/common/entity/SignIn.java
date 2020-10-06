package cn.fzn.classsign.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SignIn {
    private Integer sid;
    private Integer ssid;
    private Integer uid;
    private String uNum;
    private String name;
    private String status;
}
