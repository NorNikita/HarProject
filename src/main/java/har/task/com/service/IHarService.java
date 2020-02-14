package har.task.com.service;

import har.task.com.entity.HarFile;
import har.task.com.entity.InnerModelData;
import har.task.com.datamodel.innermodel.TestProfile;
import har.task.com.datamodel.harmodel.Har;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IHarService {

    HarFile saveFile(MultipartFile file) throws IOException;

    void sendContentInQueue(HarFile entity);

    Har getHarFile(Long id) throws IOException;

    void deleteHarFile(Long id);

    Har updateHarFile(Long id, MultipartFile multipartFile) throws IOException;

    InnerModelData saveModel(TestProfile testProfile) throws IOException;

    TestProfile transformToInnerModel(String content) throws IOException;
}
