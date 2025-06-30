package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.VO.OutpatientCostStatisticsVO;
import org.csu.healthsystem.service.OutCostStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics/cost")
public class OutCostStatisticsController {

    @Autowired
    private OutCostStatisticsService outcostStatisticsService;

    @GetMapping("/outpatient")
    public CommonResponse<List<OutpatientCostStatisticsVO>> getOutpatientCostStatistics() {
        List<OutpatientCostStatisticsVO> data = outcostStatisticsService.getOutpatientCostStatistics();
        return CommonResponse.createForSuccess(data);
    }
}