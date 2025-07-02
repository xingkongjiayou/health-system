package org.csu.healthsystem.controller;


import org.csu.healthsystem.pojo.VO.DataQualityCheckRequestVO;
import org.csu.healthsystem.pojo.VO.DataQualityCheckResultVO;
import org.csu.healthsystem.service.DataQualityCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataQualityCheckController {

    @Autowired
    private DataQualityCheckService qualityCheckService;

    @PostMapping("/quality-check")
    public Map<String, Object> qualityCheck(@RequestBody DataQualityCheckRequestVO request) {
        DataQualityCheckResultVO data = qualityCheckService.qualityCheck(request);
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "数据质量检查完成");
        resp.put("data", data);
        return resp;
    }
}
