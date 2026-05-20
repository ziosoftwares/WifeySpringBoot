package com.zio.plan.client;

import com.zio.common.data.RecipeInfo;
import com.zio.plan.data.DayPlanDTO;
import com.zio.plan.data.PlanDTO;
import com.zio.plan.data.PlanRequest;
import com.zio.plan.data.entity.Plan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

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

    public Mono<PlanDTO> generate(PlanRequest request) {
        return webClient.post().uri(method).bodyValue(request).retrieve()
                .bodyToMono(PlanDTO.class);
    }
}
