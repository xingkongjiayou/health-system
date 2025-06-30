package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.VO.InpatientCostStatisticsVO;
import org.csu.healthsystem.service.InCostStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics/cost")
public class InCostStatisticsController {

    @Autowired
    private InCostStatisticsService IncostStatisticsService;

    @GetMapping("/inpatient")
    public CommonResponse<List<InpatientCostStatisticsVO>> getInpatientCostStatistics() {
        List<InpatientCostStatisticsVO> data = IncostStatisticsService.getInpatientCostStatistics();
        return CommonResponse.createForSuccess(data);
    }
}