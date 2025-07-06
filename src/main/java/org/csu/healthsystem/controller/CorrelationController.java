package org.csu.healthsystem.controller;


import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.client.Client;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DTO.CorrelationDTO;
import org.csu.healthsystem.pojo.PO.CorrePO;
import org.csu.healthsystem.pojo.VO.CorrelationVO;
import org.csu.healthsystem.pojo.VO.PredictVO;
import org.csu.healthsystem.pojo.VO.ScatterVO;
import org.csu.healthsystem.service.BaseQueryOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/api/correlation")
@RestController
public class CorrelationController {
    @Autowired
    BaseQueryOneService baseQueryOneService;
    @Autowired
    Client client;
    @PostMapping("/heatmap")
    public CommonResponse<CorrelationVO> heatmap(@RequestBody CorrelationDTO correlationDTO) throws IOException, InterruptedException {
        List<String> toGet=correlationDTO.getColumns();
        List<List<Double>> rows =new ArrayList<>();
        for(String column:toGet){
            rows.add(baseQueryOneService.query(column,correlationDTO.getFilters()));
        }
        return CommonResponse.createForSuccess(client.heatmap(new CorrePO(toGet,rows)));
    }

    @PostMapping("/scatter")
    public CommonResponse<ScatterVO> scatter(@RequestBody CorrelationDTO correlationDTO) throws IOException, InterruptedException {
        List<String> toGet=correlationDTO.getColumns();
        List<List<Double>> rows =new ArrayList<>();
        if(toGet.size()!=2) {return CommonResponse.createForError("散点图的变量数必须为2");}
        for(String column:toGet){rows.add(baseQueryOneService.query(column,correlationDTO.getFilters()));}
        CorrelationVO correlationVO = client.heatmap(new CorrePO(toGet, rows));
        // 取两个变量的相关系数（矩阵对称，取[0][1]或[1][0]）
        Double corrValue = correlationVO.getCorrelations().get(0).get(1);
        ScatterVO result = new ScatterVO(toGet,corrValue);
        return CommonResponse.createForSuccess(result);
    }
}
