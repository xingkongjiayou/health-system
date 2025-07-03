package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.HealthPersonCategory;
import org.csu.healthsystem.pojo.DO.TotalHealthPerson;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.PageResultVO;
import org.csu.healthsystem.pojo.VO.PersonnelStructureAnalysisVO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.service.HealthPersonCategoryQueryService;
import org.csu.healthsystem.service.PersonnelService;
import org.csu.healthsystem.service.PersonnelTotalCountQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics/personnel")
public class PersonnelController {
    @Autowired
    private PersonnelService personnelService;
    @Autowired
    private HealthPersonCategoryQueryService healthPersonCategoryQueryService;
    @Autowired
    private PersonnelTotalCountQueryService personnelTotalCountQueryService;
    @GetMapping("/category-stats")
    public CommonResponse<List<HealthPersonCategory>> getCategoryStats() {
        List<HealthPersonCategory> list = personnelService.getAllHealthPersonCategory();
        return CommonResponse.createForSuccess(list);
    }
    @GetMapping("/total-count")
    public CommonResponse<List<TotalHealthPerson>> getTotalCount() {
        List<TotalHealthPerson> list = personnelService.getAllTotalHealthPerson();
        return CommonResponse.createForSuccess(list);
    }
    @GetMapping("/structure-analysis")
    public CommonResponse<PersonnelStructureAnalysisVO> getStructureAnalysis() {
        PersonnelStructureAnalysisVO vo = personnelService.getPersonnelStructureAnalysis();
        return CommonResponse.createForSuccess(vo);
    }
    @PostMapping("/category-stats/query")
    public CommonResponse<ResultVO<HealthPersonCategory>> queryCategoryStats(@RequestBody QueryDTO queryDTO) {
        ResultVO<HealthPersonCategory> result = healthPersonCategoryQueryService.query(queryDTO);
        return CommonResponse.createForSuccess(result);
    }
    @PostMapping("/total-count/query")
    public CommonResponse<ResultVO<TotalHealthPerson>> queryTotalCount(@RequestBody QueryDTO queryDTO) {
        ResultVO<TotalHealthPerson> result = personnelTotalCountQueryService.query(queryDTO);
        return CommonResponse.createForSuccess(result);
    }
}