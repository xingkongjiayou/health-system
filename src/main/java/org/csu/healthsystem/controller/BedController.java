package org.csu.healthsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.HealthBedCategory;
import org.csu.healthsystem.pojo.DO.TotalBedCount;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.BedUtilizationAnalysisVO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.service.BedService;
import org.csu.healthsystem.service.HealthBedCategoryQueryService;
import org.csu.healthsystem.service.TotalBedCountQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/statistics/bed")
public class BedController {
    @Autowired
    private BedService bedService;
    @Autowired
    private HealthBedCategoryQueryService healthBedCategoryQueryService;

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
    @PostMapping("/category-stats/query")
    public CommonResponse<ResultVO<HealthBedCategory>> queryCategoryStats(@RequestBody QueryDTO queryDTO) {
        ResultVO<HealthBedCategory> result = healthBedCategoryQueryService.query(queryDTO);
        return CommonResponse.createForSuccess(result);
    }
    @Autowired
    private TotalBedCountQueryService totalBedCountQueryService;

    @PostMapping("/total-count/query")
    public CommonResponse<ResultVO<TotalBedCount>> queryTotalCount(@RequestBody QueryDTO queryDTO) {
        ResultVO<TotalBedCount> result = totalBedCountQueryService.query(queryDTO);
        return CommonResponse.createForSuccess(result);
    }
}