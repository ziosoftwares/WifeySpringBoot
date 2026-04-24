package com.zio.wifey;

import com.zio.data.dto.DayPlanDTO;
import com.zio.plan.service.PlanService;
import com.zio.util.ZioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PlanServiceLiteralTest {

    @Autowired
    private PlanService service;

    //@Test
    public void makePlanForDaysTest() throws ZioException {
        Mono<List<DayPlanDTO>> result = service.makePlanForDays(1, 8L);
        StepVerifier.create(result).assertNext(res -> {
            assertThat(res.size()).isEqualTo(3);
        }).verifyComplete();
    }
}
