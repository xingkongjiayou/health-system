package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.HospitalServiceStatistics;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.pojo.VO.ServiceQualityAnalysisVO;
import org.csu.healthsystem.service.HospitalServiceStatisticsQueryService;
import org.csu.healthsystem.service.ServiceStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/statistics/service")
public class ServiceStatisticsController {
    @Autowired
    private ServiceStatisticsService serviceStatisticsService;

    @GetMapping("/hospital-service")
    public CommonResponse<List<HospitalServiceStatistics>> getHospitalService() {
        List<HospitalServiceStatistics> list = serviceStatisticsService.getAllHospitalServiceStatistics();
        return CommonResponse.createForSuccess(list);
    }
    @GetMapping("/quality-analysis")
    public CommonResponse<ServiceQualityAnalysisVO> getQualityAnalysis(
            @RequestParam String hospitalType,
            @RequestParam String analysisType) {
        ServiceQualityAnalysisVO vo = serviceStatisticsService.getServiceQualityAnalysis(hospitalType, analysisType);
        return CommonResponse.createForSuccess(vo);
    }
    @Autowired
    private HospitalServiceStatisticsQueryService service;

    @PostMapping("/hospital-service/query")
    public CommonResponse<ResultVO<HospitalServiceStatistics>> query(@RequestBody QueryDTO queryDTO) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ResultVO<HospitalServiceStatistics> result = service.query(queryDTO);
        return CommonResponse.createForSuccess(result);
    }
}