package org.csu.healthsystem.pojo.DO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionCategoryStats {
    Integer year;
    Integer hospital;
    Integer communityHealth;
    Integer healthCenter;
    Integer cdc;
    Integer mch;
    Integer total;
}
