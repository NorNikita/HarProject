package har.task.com.listener;

import har.task.com.service.IHarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@EnableRabbit
@Controller
public class RabbitMQListener {

    private IHarService service;

    @Autowired
    public RabbitMQListener(IHarService service) {
        this.service = service;
    }

    @RabbitListener(queues = "harQueue")
    public void getHarFile(String content) throws IOException {
        service.saveModel(content);
        log.info("inner model data saved in database!");
    }
}
