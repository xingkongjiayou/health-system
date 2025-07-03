package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.InpatientCostStatisticsVO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.service.InCostStatisticsService;
import org.csu.healthsystem.service.InpatientCostsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private InpatientCostsQueryService inpatientCostsQueryService;

    @PostMapping("/inpatient/query")
    public CommonResponse<ResultVO<InpatientCostStatisticsVO>> query(@RequestBody QueryDTO queryDTO) {
        ResultVO<InpatientCostStatisticsVO> result = inpatientCostsQueryService.query(queryDTO);
        return CommonResponse.createForSuccess(result);
    }
}