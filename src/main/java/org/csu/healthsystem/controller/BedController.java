package org.csu.healthsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.HealthBedCategory;
import org.csu.healthsystem.pojo.DO.TotalBedCount;
import org.csu.healthsystem.pojo.VO.BedUtilizationAnalysisVO;
import org.csu.healthsystem.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/statistics/bed")
public class BedController {
    @Autowired
    private BedService bedService;

    @GetMapping("/category-stats")
    public CommonResponse<List<HealthBedCategory>> getCategoryStats() {
        List<HealthBedCategory> list = bedService.getAllHealthBedCategory();
        return CommonResponse.createForSuccess(list);
    }
    @GetMapping("/total-count")
    public CommonResponse<List<TotalBedCount>> getTotalCount() {
        List<TotalBedCount> list = bedService.getAllTotalBedCount();
        return CommonResponse.createForSuccess(list);
    }
    @GetMapping("/utilization-analysis")
    public CommonResponse<BedUtilizationAnalysisVO> getUtilizationAnalysis(
            @RequestParam Integer year,
            @RequestParam String hospitalType) {
        BedUtilizationAnalysisVO vo = bedService.getBedUtilizationAnalysis(year, hospitalType);
        return CommonResponse.createForSuccess(vo);
    }
}