package com.pflb.hartask.listener;

import com.pflb.hartask.model.innermodel.TestProfile;
import com.pflb.hartask.entity.InnerModelData;
import com.pflb.hartask.service.IHarService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@EnableRabbit
@Component
public class RabbitMQListener {

    @Getter
    private int counter;
    private IHarService service;

    public RabbitMQListener(IHarService service) {
        this.service = service;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queueName}")
    public void getHarFile(String content) throws IOException {
        counter++;
        TestProfile testProfile = service.transformToInnerModel(content);
        InnerModelData innerModelData = service.saveModel(testProfile);
        log.info("inner harmodel data saved in database! Count request {}", innerModelData.getCountRequest());
    }

    public void initCounter() {
        this.counter = 0;
    }
}
