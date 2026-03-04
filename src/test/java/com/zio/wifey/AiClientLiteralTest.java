package com.zio.wifey;

import com.zio.client.AiClient;
import com.zio.data.dto.GeneralDTO;
import com.zio.data.dto.MealsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AiClientLiteralTest {

    @Autowired
    AiClient client;

    @Test
    public void literal_generate_test() {
        MealsRequest request = new MealsRequest(1, List.of(
                new GeneralDTO(89L, "Dosa with coconut chutney"),
                new GeneralDTO(56L, "Sambar rice"),
                new GeneralDTO(8L, "Noodles")
        ));
        Mono<List<Long>> resultMono = client.generate(request);

        StepVerifier.create(resultMono)
                .assertNext(result -> {
                    System.out.println("result = " + result);
                    assertThat(result).isNotEmpty();
                    assertThat(result.get(0)).isInstanceOf(Long.class);
                    assertThat(result.size()).isEqualTo(3);
                })
                .verifyComplete();
    }
}
