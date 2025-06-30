package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.VO.DataImportResultVO;
import org.csu.healthsystem.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/data")
public class DataImportController {

    @Autowired
    private DataImportService dataImportService;

    @PostMapping("/import")
    public CommonResponse<DataImportResultVO> importData(
            @RequestParam("file") MultipartFile file,
            @RequestParam("dataType") String dataType,
            @RequestParam("importMode") String importMode) {
        DataImportResultVO result = dataImportService.importData(file, dataType, importMode);
        return CommonResponse.createForSuccess("数据导入成功", result);
    }
}