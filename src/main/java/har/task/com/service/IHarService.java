package har.task.com.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IHarService {

    void saveFile(MultipartFile file) throws IOException;
}
