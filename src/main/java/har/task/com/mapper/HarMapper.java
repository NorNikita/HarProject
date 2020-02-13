package har.task.com.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import har.task.com.mapper.innermodel.TestProfile;
import har.task.com.mapper.innermodel.Request;
import har.task.com.mapper.model.Har;
import har.task.com.mapper.model.entry.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Har mapFromString(String content) throws IOException {
        return  mapper.readValue(content, Har.class);
    }

    public TestProfile transformToInnerModel(String content) throws IOException {

        Har har = this.mapFromString(content);
        TestProfile testProfile = new TestProfile();

        testProfile.setRequests(har.getLog().getEntries().stream().map(entries -> {
            HarRequest harRequest = entries.getRequest();

            String url = harRequest.getUrl();
            String body = harRequest.getPostData() != null ? harRequest.getPostData().getText() : null;
            Map<String, String> headers = getHeaders(harRequest);
            Map<String, String> params = getParams(harRequest);
            HttpMethod status = harRequest.getMethod();

            return new Request(url, body, headers, params, status, 0.0);
        }).collect(Collectors.toList()));

        return testProfile;
    }

    public String asString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    private Map<String, String> getParams(HarRequest harRequest) {

        HarPostData postData = harRequest.getPostData();
        Map<String, String> paramsFromPostData = new HashMap<>();

        if (postData != null) {
            paramsFromPostData = postData.getParams().stream()
                    .collect(Collectors.toMap(HarParams::getName, HarParams::getValue));
        }

        Map<String, String> paramsFromQueryString = harRequest.getQueryString().stream()
                .collect(Collectors.toMap(HarQueryString::getName, HarQueryString::getValue));

        return Stream.concat(paramsFromPostData.entrySet().stream(), paramsFromQueryString.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, String> getHeaders(HarRequest harRequest) {
        return harRequest.getHeaders().stream().collect(Collectors.toMap(HarHeaders::getName, HarHeaders::getValue));
    }
}
