package com.pflb.hartask.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pflb.hartask.controller.exception.HarFileNotFoundException;
import com.pflb.hartask.entity.HarFile;
import com.pflb.hartask.entity.InnerModelData;
import com.pflb.hartask.datamodel.innermodel.Request;
import com.pflb.hartask.datamodel.innermodel.TestProfile;
import com.pflb.hartask.datamodel.harmodel.Har;
import com.pflb.hartask.datamodel.harmodel.entry.*;
import com.pflb.hartask.repository.HarFileRepository;
import com.pflb.hartask.repository.InnerModelDataRepository;
import com.pflb.hartask.service.IHarService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HarServiceImpl implements IHarService {

    @Value("${spring.rabbitmq.queueName}")
    private String queueName;

    private ObjectMapper objectMapper;
    private HarFileRepository harRepository;
    private RabbitTemplate rabbitTemplate;
    private InnerModelDataRepository modelRepository;

    @Autowired
    public HarServiceImpl(ObjectMapper objectMapper, HarFileRepository harRepository, RabbitTemplate rabbitTemplate, InnerModelDataRepository modelRepository) {
        this.objectMapper = objectMapper;
        this.harRepository = harRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.modelRepository = modelRepository;
    }

    @Override
    public HarFile saveFile(MultipartFile multipartFile) throws IOException {
        Har har = objectMapper.readValue(multipartFile.getInputStream(), Har.class);
        HarLog log = har.getLog();

        String version = log.getVersion();
        String nameBrowser = log.getBrowser() == null ? null : log.getBrowser().getName();
        String content = objectMapper.writeValueAsString(har);

        return harRepository.save(new HarFile(nameBrowser, version, content));
    }

    @Override
    public void sendContentInQueue(HarFile entity) {
        rabbitTemplate.convertAndSend(queueName, entity.getContent());
    }

    @Override
    public Har getHarFile(Long id) throws IOException {
        HarFile harFile = harRepository.findById(id).orElseThrow(() -> new HarFileNotFoundException("File with id = " + id + " not found"));
        return objectMapper.readValue(harFile.getContent(), Har.class);
    }

    @Override
    public void deleteHarFile(Long id) {
        harRepository.deleteById(id);
    }

    @Override
    public Har updateHarFile(Long id, MultipartFile multipartFile) throws IOException {
        HarFile harFile = harRepository.findById(id)
                .orElseThrow(() -> new HarFileNotFoundException("File with id = " + id + " not found! Can not update"));

        Har har = objectMapper.readValue(multipartFile.getInputStream(), Har.class);
        HarLog log = har.getLog();

        harFile.setVersion(log.getVersion());
        harFile.setBrowser(log.getBrowser() == null ? null : log.getBrowser().getName());
        String content = objectMapper.writeValueAsString(har);
        harFile.setContent(content);

        harRepository.save(harFile);
        return objectMapper.readValue(content, Har.class);
    }

    @Override
    public InnerModelData saveModel(TestProfile testProfile) throws IOException {
        Long countRequest = (long) testProfile.getRequests().size();
        String data = objectMapper.writeValueAsString(testProfile);

        return modelRepository.save(new InnerModelData(countRequest, data));
    }

    @Override
    public TestProfile transformToInnerModel(String content) throws IOException {
        Har har = objectMapper.readValue(content, Har.class);
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
