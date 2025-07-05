package org.csu.healthsystem.controller;

import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.Condition;
import org.csu.healthsystem.pojo.DTO.QueryDTO;
import org.csu.healthsystem.pojo.VO.OutpatientCostStatisticsVO;
import org.csu.healthsystem.pojo.VO.ResultVO;
import org.csu.healthsystem.service.OutpatientCostsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics/cost/outpatient")
public class OutpatientCostsQueryController {
    @Autowired
    private OutpatientCostsQueryService outpatientCostsQueryService;

    @PostMapping("/query")
    public CommonResponse<ResultVO<OutpatientCostStatisticsVO>> query(@RequestBody QueryDTO queryDTO) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
            // 针对 hospitalLevel 字段做类型转换
            Map<String, Condition> filters = queryDTO.getFilters();
            if (filters != null && filters.containsKey("hospitalLevel")) {
                Condition cond = filters.get("hospitalLevel");
                if (cond.getEq() != null && !(cond.getEq() instanceof String)) {
                    cond.setEq(String.valueOf(cond.getEq()));
                }
                if (cond.getNotEq() != null && !(cond.getNotEq() instanceof String)) {
                    cond.setNotEq(String.valueOf(cond.getNotEq()));
                }
            // 继续业务逻辑
            ResultVO<OutpatientCostStatisticsVO> result = outpatientCostsQueryService.query(queryDTO);
            return CommonResponse.createForSuccess(result);
        }
        ResultVO<OutpatientCostStatisticsVO> result = outpatientCostsQueryService.query(queryDTO);
        return CommonResponse.createForSuccess(result);
    }
}
