package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.HospitalLevelStats;
import org.csu.healthsystem.pojo.DO.HospitalStatistics;
import org.csu.healthsystem.pojo.DO.InstitutionCategoryStats;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.service.HospitalLevelStatsService;
import org.csu.healthsystem.service.HospitalStatisticsService;
import org.csu.healthsystem.service.InstitutionCategoryStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {
    @Autowired
    HospitalLevelStatsService hospitalLevelStatsService;
    @Autowired
    HospitalStatisticsService hospitalStatisticsService;
    @Autowired
    InstitutionCategoryStatsService institutionCategoryStatsService;

    @PostMapping("/levelstats")
    public CommonResponse<ResultVO<HospitalLevelStats>> queryHospitalLevelStats(@RequestBody QueryDTO queryDTO) {
        return CommonResponse.createForSuccess(hospitalLevelStatsService.query(queryDTO));
    }

    @PostMapping("/statistics")
    public CommonResponse<ResultVO<HospitalStatistics>> queryHospitalStatistics(@RequestBody QueryDTO queryDTO) {
        return CommonResponse.createForSuccess(hospitalStatisticsService.query(queryDTO));
    }

    @PostMapping("/institutionstats")
    public CommonResponse<ResultVO<InstitutionCategoryStats>> queryInstitutionStats(@RequestBody QueryDTO queryDTO) {
        return CommonResponse.createForSuccess(institutionCategoryStatsService.query(queryDTO));
    }
}
