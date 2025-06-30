package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.VO.CostStructureComparisonVO;
import org.csu.healthsystem.service.CostStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics/cost")
public class CostStatisticsController {
    @Autowired
    private CostStatisticsService costStatisticsService;
    @GetMapping("/structure-comparison")
    public CommonResponse<CostStructureComparisonVO> getCostStructureComparison(
            @RequestParam String compareType,
            @RequestParam String costType) {
        CostStructureComparisonVO vo = costStatisticsService.getCostStructureComparison(compareType, costType);
        return CommonResponse.createForSuccess(vo);
    }
}
