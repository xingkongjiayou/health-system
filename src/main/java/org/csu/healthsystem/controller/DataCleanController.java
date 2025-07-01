package org.csu.healthsystem.controller;


import org.csu.healthsystem.pojo.VO.DataCleanRequestVO;
import org.csu.healthsystem.pojo.VO.DataCleanResultVO;
import org.csu.healthsystem.service.DataCleanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataCleanController {

    @Autowired
    private DataCleanService dataCleanService;

    @PostMapping("/clean")
    public Object cleanData(@RequestBody DataCleanRequestVO request) {
        DataCleanResultVO result = dataCleanService.cleanData(request);
        return Map.of(
                "code", 200,
                "message", "数据清洗完成",
                "data", result
        );
    }
}
