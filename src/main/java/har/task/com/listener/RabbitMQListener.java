package har.task.com.listener;

import har.task.com.entity.HarFile;
import har.task.com.mapper.HarMapper;
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
    @Autowired
    private HarMapper mapper;

    @RabbitListener(queues = "harQueue")
    public void getHarFile(HarFile harFile) throws IOException {
        log.info("getMessage {}", "some message");
    }
}
