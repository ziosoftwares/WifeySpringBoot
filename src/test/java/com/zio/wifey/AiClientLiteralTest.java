package com.zio.wifey;

import com.zio.plan.client.AiClient;
import com.zio.common.data.GeneralDTO;
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


}
