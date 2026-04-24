package com.zio.plan.client;

import com.zio.data.dto.MealsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class AiClient {

    private final WebClient webClient;
    private final String method;

    public AiClient(WebClient.Builder builder,
                    @Value("${ai.url}") String baseUrl,
                    @Value("${ai.method}") String method) {
        this.webClient = builder.baseUrl(baseUrl).build();
        this.method = method;
    }

    public Mono<List<Long>> generate(MealsRequest request) {
        return webClient.post().uri(method).bodyValue(request).retrieve()
                .bodyToFlux(Long.class).collectList();
    }
}
