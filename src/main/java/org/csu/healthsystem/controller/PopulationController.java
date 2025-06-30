package org.csu.healthsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.*;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/population")
@RestController
public class PopulationController {
    @Autowired
    private PopulationBasicService populationBasicService;
    @Autowired
    private PopulationChangeService populationChangeService;
    @Autowired
    private PopulationDeFactoService populationDeFactoService;
    @Autowired
    private PopulationGenderService populationGenderService;
    @Autowired
    private PopulationResidentService populationResidentService;
    @Autowired
    private PopulationRegionService populationRegionService;

    @PostMapping("/basic")
    public CommonResponse<ResultVO<PopulationBasic>> queryPopulation(@RequestBody QueryDTO queryDTO) {
        return CommonResponse.createForSuccess(populationBasicService.query(queryDTO));
    }

    @PostMapping("/change")
    public CommonResponse<ResultVO<PopulationChange>> queryPopulationChange(@RequestBody QueryDTO queryDTO) {
        return CommonResponse.createForSuccess(populationChangeService.query(queryDTO));
    }

    @PostMapping("/defacto")
    public CommonResponse<ResultVO<PopulationDeFacto>> queryPopulationDeFacto(@RequestBody QueryDTO queryDTO) {
        return CommonResponse.createForSuccess(populationDeFactoService.query(queryDTO));
    }

    @PostMapping("/gender")
    public CommonResponse<ResultVO<PopulationGender>> queryPopulationGender(@RequestBody QueryDTO queryDTO) {
        return CommonResponse.createForSuccess(populationGenderService.query(queryDTO));
    }

    @PostMapping("/resident")
    public CommonResponse<ResultVO<PopulationResident>> queryPopulationResident(@RequestBody QueryDTO queryDTO) {
        return CommonResponse.createForSuccess(populationResidentService.query(queryDTO));
    }

    @PostMapping("/region")
    public CommonResponse<ResultVO<PopulationRegion>> queryPopulationRegion(@RequestBody QueryDTO queryDTO) {
        return CommonResponse.createForSuccess(populationRegionService.query(queryDTO));
    }
}
