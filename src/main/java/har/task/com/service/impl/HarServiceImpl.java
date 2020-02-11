package har.task.com.service.impl;

import har.task.com.entity.HarFile;
import har.task.com.mapper.HarMapper;
import har.task.com.mapper.model.Har;
import har.task.com.mapper.model.entry.HarLog;
import har.task.com.repository.HarFileRepository;
import har.task.com.service.IHarService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class HarServiceImpl implements IHarService {

    private HarMapper harMapper;
    private HarFileRepository repository;
    private RabbitTemplate rabbitTemplate;

    @Value("${upload.path}")
    private String path;

    @Autowired
    public HarServiceImpl(HarMapper harMapper, HarFileRepository repository, RabbitTemplate rabbitTemplate) {
        this.harMapper = harMapper;
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public HarFile saveFile(MultipartFile multipartFile) throws IOException {
        File file = new File(path + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        Har har = harMapper.mapFromFile(file);
        HarLog log = har.getLog();

        String version = log.getVersion();
        String nameBrowser = log.getBrowser() == null ? null : log.getBrowser().getName();
        String content = new String(Files.readAllBytes(file.toPath()));

        file.deleteOnExit();
        return repository.save(new HarFile(version, nameBrowser, content));
    }

    @Override
    public void sendContentInQueue(HarFile entity) {
        rabbitTemplate.convertAndSend("harQueue", entity.getContent());
    }
}
