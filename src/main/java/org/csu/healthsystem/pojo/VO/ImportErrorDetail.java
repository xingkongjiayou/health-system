package org.csu.healthsystem.pojo.VO;

import lombok.Data;

@Data
public class ImportErrorDetail {
    private int row;
    private String error;

    public ImportErrorDetail() {}

    public ImportErrorDetail(int row, String error) {
        this.row = row;
        this.error = error;
    }

}
