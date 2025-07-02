package org.csu.healthsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.client.Client;
import org.csu.healthsystem.common.CommonResponse;
import org.csu.healthsystem.pojo.DO.ArimaParameter;
import org.csu.healthsystem.pojo.DO.SarimaParameter;
import org.csu.healthsystem.pojo.DTO.PredictDTO;
import org.csu.healthsystem.pojo.PO.PrePO;
import org.csu.healthsystem.pojo.VO.PredictVO;
import org.csu.healthsystem.service.BaseQueryOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/api/predict")
@RestController
public class PredictController {
    @Autowired
    BaseQueryOneService baseQueryOneService;
    @Autowired
    Client client;
    @PostMapping("/arima")
    public CommonResponse<PredictVO> arima(@RequestBody PredictDTO<ArimaParameter> predictDTO) throws IOException, InterruptedException {
        List<Double> series=baseQueryOneService.query(predictDTO.getPredictDO());
        if(series.isEmpty()) return CommonResponse.createForError(403,"输入属性名错误或存在SQL注入风险");
        PrePO<ArimaParameter> prePO =new PrePO<>(series,predictDTO.getParameter());
        return CommonResponse.createForSuccess(client.arima(prePO));
    }

    @PostMapping("/sarima")
    public CommonResponse<PredictVO> sarima(@RequestBody PredictDTO<SarimaParameter> predictDTO) throws IOException, InterruptedException {
        List<Double> series=baseQueryOneService.query(predictDTO.getPredictDO());
        if (series.isEmpty()) return CommonResponse.createForError(403,"输入属性名错误或存在SQL注入风险");
        PrePO<SarimaParameter> prePO =new PrePO<>(series,predictDTO.getParameter());
        return CommonResponse.createForSuccess(client.sarima(prePO));
    }

}
