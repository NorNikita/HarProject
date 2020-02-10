package har.task.com.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import har.task.com.mapper.model.Har;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class HarMapper {

    private ObjectMapper mapper;

    public HarMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule());
    }

    public Har mapFromFile(File file) throws IOException {
        return mapper.readValue(file, Har.class);
    }
}
