package org.csu.healthsystem.controller;


import lombok.RequiredArgsConstructor;
import org.csu.healthsystem.client.DeepSeekClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/deepseek")
public class DeepSeekController {
    private final DeepSeekClient svc;

    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody Map<String,String> req){
        return svc.chat(req.get("prompt"));
    }

    @GetMapping(value="/chat/stream", produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestParam String prompt){
        return svc.chatStream(prompt);
    }
}
