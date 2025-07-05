package org.csu.healthsystem.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalLevelStats {
    // 对应表 hospital_level_stats 的字段（已由蛇形转驼峰）
    private Integer id;
    private Integer year;
    private Integer level3Total;
    private Integer level3a;
    private Integer level3b;
    private Integer level3Ungraded;
    private Integer level2Total;
    private Integer level2a;
    private Integer level2b;
    private Integer level2Ungraded;
    private Integer level1Ungraded;

}
