package cn.fzn.classsign.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ClassJoin {
    private Integer cjid;
    private Integer cid;
    private Integer uid;
}
