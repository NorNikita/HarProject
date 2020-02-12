package har.task.com.service;

import har.task.com.entity.HarFile;
import har.task.com.mapper.model.Har;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IHarService {

    HarFile saveFile(MultipartFile file) throws IOException;

    void sendContentInQueue(HarFile entity);

    Har getHarFile(Long id) throws IOException;

    void deleteHarFile(Long id);

    Har updateHarFile(Long id, MultipartFile multipartFile) throws IOException;
}
