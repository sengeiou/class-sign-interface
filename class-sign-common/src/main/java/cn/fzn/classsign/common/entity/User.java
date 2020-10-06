package cn.fzn.classsign.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    private Integer uid;
    private String phone;
    private String uNum;
    private String name;
    private String sex;
    private String password;
}
