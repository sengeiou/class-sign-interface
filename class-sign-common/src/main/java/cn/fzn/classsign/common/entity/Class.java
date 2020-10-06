package cn.fzn.classsign.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Class {
    private Integer cid;
    private String cNum;
    private String name;
    private Integer uid;
    private String tName;
    private String joinCode;
    private Integer total;
}
