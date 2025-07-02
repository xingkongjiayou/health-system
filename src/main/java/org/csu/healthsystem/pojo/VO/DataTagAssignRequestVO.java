package org.csu.healthsystem.pojo.VO;


import lombok.Data;
import java.util.List;

@Data
public class DataTagAssignRequestVO {
    private String dataType;    // 如 population、institution 等
    private List<Long> dataIds; // 需要打标签的数据主键ID
    private List<Long> tagIds;  // 需要绑定的标签ID
}
