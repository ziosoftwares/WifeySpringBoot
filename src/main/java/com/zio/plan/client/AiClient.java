package com.zio.plan.client;

import com.zio.plan.data.PlanDTO;
import com.zio.plan.data.PlanRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AiClient {

    private final WebClient webClient;
    private static final String path = "generate";

    public AiClient(WebClient.Builder builder,
                    @Value("${ai.url}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public Mono<PlanDTO> generate(PlanRequest request) {
        return webClient.post().uri(path).bodyValue(request).retrieve()
                .bodyToMono(PlanDTO.class);
    }
}
