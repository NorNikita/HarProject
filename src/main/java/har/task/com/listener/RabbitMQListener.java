package har.task.com.listener;

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

    private HarMapper mapper;

    @Autowired
    public RabbitMQListener(HarMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = "harQueue")
    public void getHarFile(String content) throws IOException {
        log.info("Version {}", mapper.mapFromString(content).getLog().getVersion());
    }
}
