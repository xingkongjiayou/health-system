package org.csu.healthsystem.controller;

import org.csu.healthsystem.pojo.VO.DataImportExportLogVO;
import org.csu.healthsystem.pojo.VO.PageResultVO;
import org.csu.healthsystem.service.DataImportExportLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataImportExportLogController {

    @Autowired
    private DataImportExportLogService logService;

    @GetMapping("/import-export-logs")
    public Map<String, Object> getLogs(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String status
    ) {
        PageResultVO<DataImportExportLogVO> data = logService.getLogs(page, size, operation, status);
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "操作成功");
        resp.put("data", data);
        return resp;
    }
}
