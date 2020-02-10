package har.task.com.service;

import har.task.com.entity.HarFile;
import har.task.com.mapper.HarMapper;
import har.task.com.mapper.model.Har;
import har.task.com.mapper.model.entry.HarLog;
import har.task.com.repository.HarFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class HarService {

    private HarMapper harMapper;
    private HarFileRepository repository;

    @Value("${upload.path}")
    private String path;

    @Autowired
    public HarService(HarMapper harMapper, HarFileRepository repository) {
        this.harMapper = harMapper;
        this.repository = repository;
    }

    public void saveFile(MultipartFile multipartFile) throws IOException {
        File file = new File(path + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        Har har = harMapper.mapFromFile(file);
        HarLog log = har.getLog();

        String version = log.getVersion();
        String nameBrowser = log.getBrowser().getName();
        String content = new String(Files.readAllBytes(file.toPath()));

        file.deleteOnExit();
        repository.save(new HarFile(version, nameBrowser, content));
    }
}
