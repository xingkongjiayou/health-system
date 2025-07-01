package org.csu.healthsystem.pojo.VO;


import lombok.Data;
import java.util.List;

@Data
public class PageResultVO<T> {
    private long total;
    private List<T> records;
}
