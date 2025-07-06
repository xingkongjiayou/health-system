package org.csu.healthsystem.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.csu.healthsystem.pojo.DO.ArimaParameter;
import org.csu.healthsystem.pojo.DO.NeuroProphetParameter;
import org.csu.healthsystem.pojo.DO.SarimaParameter;
import org.csu.healthsystem.pojo.DO.TbatsParameter;
import org.csu.healthsystem.pojo.PO.CorrePO;
import org.csu.healthsystem.pojo.PO.PrePO;
import org.csu.healthsystem.pojo.VO.CorrelationVO;
import org.csu.healthsystem.pojo.VO.PredictVO;
import org.csu.healthsystem.pojo.VO.ScatterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class Client {
    @Autowired
    ObjectMapper objectMapper;
    public PredictVO arima(PrePO<ArimaParameter> arimapo) throws IOException, InterruptedException {
        String url = "http://localhost:5000/predict/arima";
        String json=objectMapper.writeValueAsString(arimapo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")   // ←✅ 拼写修正
                .header("Accept", "application/json")        // 可选：声明期待 JSON
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        List<Double> forecast=objectMapper.readValue(response.body(),new TypeReference<>(){});
        PredictVO predictVO=new PredictVO();
        predictVO.setSeries(arimapo.getSeries());
        predictVO.setForecast(forecast);
        return predictVO;
    }

    public PredictVO sarima(PrePO<SarimaParameter> sarimapo) throws IOException, InterruptedException {
        String url = "http://localhost:5000/predict/sarima";
        String json=objectMapper.writeValueAsString(sarimapo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")   // ←✅ 拼写修正
                .header("Accept", "application/json")        // 可选：声明期待 JSON
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        List<Double> forecast=objectMapper.readValue(response.body(),new TypeReference<>(){});
        PredictVO predictVO=new PredictVO();
        predictVO.setSeries(sarimapo.getSeries());
        predictVO.setForecast(forecast);
        return predictVO;
    }

    public PredictVO neuroprophet(PrePO<NeuroProphetParameter> prePO) throws IOException, InterruptedException {
        String url = "http://localhost:5000/predict/neuroprophet";
        String json=objectMapper.writeValueAsString(prePO);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")   // ←✅ 拼写修正
                .header("Accept", "application/json")        // 可选：声明期待 JSON
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        List<Double> forecast=objectMapper.readValue(response.body(),new TypeReference<>(){});
        PredictVO predictVO=new PredictVO();
        predictVO.setSeries(prePO.getSeries());
        predictVO.setForecast(forecast);
        return predictVO;
    }

    public PredictVO tbats(PrePO<TbatsParameter> po) throws IOException, InterruptedException {
        String url = "http://localhost:5000/predict/tbats";
        String json=objectMapper.writeValueAsString(po);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")   // ←✅ 拼写修正
                .header("Accept", "application/json")        // 可选：声明期待 JSON
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        List<Double> forecast=objectMapper.readValue(response.body(),new TypeReference<>(){});
        PredictVO predictVO=new PredictVO();
        predictVO.setSeries(po.getSeries());
        predictVO.setForecast(forecast);
        return predictVO;
    }

    public CorrelationVO heatmap(CorrePO correPO) throws IOException, InterruptedException {
        String url = "http://localhost:5000/correlation/heatmap";
        String json=objectMapper.writeValueAsString(correPO);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")   // ←✅ 拼写修正
                .header("Accept", "application/json")        // 可选：声明期待 JSON
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), CorrelationVO.class);
    }

}
