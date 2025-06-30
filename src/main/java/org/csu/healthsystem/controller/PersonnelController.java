package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.HealthPersonCategory;
import org.csu.healthsystem.pojo.DO.TotalHealthPerson;
import org.csu.healthsystem.pojo.VO.PersonnelStructureAnalysisVO;
import org.csu.healthsystem.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics/personnel")
public class PersonnelController {
    @Autowired
    private PersonnelService personnelService;

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
}