package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.VO.DataExportRequestVO;
import org.csu.healthsystem.pojo.VO.DataExportResultVO;
import org.csu.healthsystem.service.DataExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class DataExportController {

    @Autowired
    private DataExportService dataExportService;

    @PostMapping("/export")
    public CommonResponse<DataExportResultVO> exportData(@RequestBody DataExportRequestVO request) {
        DataExportResultVO result = dataExportService.exportData(request);
        return CommonResponse.createForSuccess("导出任务已创建", result);
    }
}
