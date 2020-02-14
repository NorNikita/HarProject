package har.task.com.listener;

import har.task.com.entity.InnerModelData;
import har.task.com.datamodel.innermodel.TestProfile;
import har.task.com.service.IHarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@EnableRabbit
@Component
public class RabbitMQListener {

    private IHarService service;

    @Autowired
    public RabbitMQListener(IHarService service) {
        this.service = service;
    }

    @RabbitListener(queues = "harQueue")
    public void getHarFile(String content) throws IOException {
        TestProfile testProfile = service.transformToInnerModel(content);
        InnerModelData innerModelData = service.saveModel(testProfile);

        log.info("inner harmodel data saved in database! Count request {}", innerModelData.getCountRequest());
    }
}
