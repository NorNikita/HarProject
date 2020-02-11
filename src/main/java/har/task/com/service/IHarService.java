package har.task.com.service;

import har.task.com.entity.HarFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IHarService {

    HarFile saveFile(MultipartFile file) throws IOException;

    void sendContentInQueue(HarFile entity);
}
