package har.task.com.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import har.task.com.mapper.model.Har;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class HarMapper {

    private ObjectMapper mapper;

    public HarMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule());
    }

    public Har mapFromFile(MultipartFile multipartFile) throws IOException {

        try {
            File file = new File("/home/nikita/" + multipartFile.getName());
            multipartFile.transferTo(file);
            Har har = mapper.readValue(file, Har.class);
            file.delete();

            return har;
        } catch(IOException e) {
            throw new IOException(e);
        }
    }

}
