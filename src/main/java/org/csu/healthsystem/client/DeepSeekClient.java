package org.csu.healthsystem.client;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.csu.healthsystem.pojo.PO.DeepSeekProps;
import org.csu.healthsystem.pojo.VO.DeepSeekResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DeepSeekClient {

    private final DeepSeekProps props;     // 已通过构造器注入
    private final WebClient webClient;     // 也做成 final

    // 自定义构造器，同时利用 Spring 注入 WebClient.Builder
    public DeepSeekClient(DeepSeekProps props, WebClient.Builder builder) {
        this.props = props;
        this.webClient = builder
                .baseUrl(props.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + props.getApiKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /** 同步响应 */
    public Mono<String> chat(String prompt) {
        Map<String,Object> body = Map.of(
                "model", props.getModel(),
                "messages", List.of(Map.of("role","user","content",prompt)),
                "temperature", 0.7
        );

        return webClient          // ← 改这里
                .post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(DeepSeekResponse.class)
                .map(r -> r.getChoices().get(0).getMessage().getContent());
    }

    public Flux<String> chatStream(String prompt){
        Map<String,Object> body = Map.of(
                "model", props.getModel(),
                "messages", List.of(Map.of("role","user","content", prompt)),
                "stream", true
        );
        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(String.class)
                .filter(l -> l.startsWith("data: "))
                .map(l -> l.substring(6))
                .takeUntil("[DONE]"::equals);
    }
}
