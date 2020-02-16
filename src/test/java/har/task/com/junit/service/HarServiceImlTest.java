package har.task.com.junit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import har.task.com.controller.exception.FileNotFoundException;
import har.task.com.datamodel.harmodel.Har;
import har.task.com.datamodel.harmodel.entry.HarBrowser;
import har.task.com.datamodel.harmodel.entry.HarLog;
import har.task.com.datamodel.harmodel.entry.HttpMethod;
import har.task.com.datamodel.innermodel.Request;
import har.task.com.datamodel.innermodel.TestProfile;
import har.task.com.entity.HarFile;
import har.task.com.entity.InnerModelData;
import har.task.com.repository.HarFileRepository;
import har.task.com.repository.InnerModelDataRepository;
import har.task.com.service.impl.HarServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HarServiceImlTest {
    ObjectMapper mapper = new ObjectMapper();

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HarFileRepository harRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private InnerModelDataRepository modelRepository;

    @InjectMocks
    private HarServiceImpl service;

    @Test
    public void saveFileTest() throws IOException {
        Har har = generateHar();
        when(objectMapper.readValue(any(InputStream.class), eq(Har.class))).thenReturn(har);
        when(objectMapper.writeValueAsString(any(Har.class))).thenReturn(mapper.writeValueAsString(har));

        HarLog log = har.getLog();
        HarFile file = new HarFile(log.getBrowser().getName(), log.getVersion(), mapper.writeValueAsString(har));
        when(harRepository.save(any(HarFile.class))).thenReturn(file);

        MockMultipartFile multipartFile = new MockMultipartFile("file", "some.har", MediaType.APPLICATION_JSON_VALUE, new byte[]{});
        HarFile createByService = service.saveFile(multipartFile);

        assertEquals(log.getBrowser().getName(), createByService.getBrowser());
        assertEquals(log.getVersion(),createByService.getVersion());
        assertEquals(new ObjectMapper().writeValueAsString(har), createByService.getContent());
    }

    @Test
    public void getHarFileTest() throws IOException {
        Har har = generateHar();
        HarFile harFile = new HarFile(har.getLog().getBrowser().getName(), har.getLog().getVersion(), mapper.writeValueAsString(har));

        when(harRepository.findById(anyLong())).thenReturn(Optional.of(harFile));
        when(objectMapper.readValue(anyString(), eq(Har.class))).thenReturn(har);

        Har getByService = service.getHarFile(7L);
        assertEquals(getByService.getLog().getBrowser().getName(), har.getLog().getBrowser().getName());

        when(harRepository.findById(10L)).thenThrow(new FileNotFoundException("File with id = " + 10 + " not found! Can not update"));
        Throwable thrown = catchThrowable(() -> {
            service.getHarFile(10L);
        });

        assertEquals(thrown.getMessage(), "File with id = 10 not found! Can not update");
    }

    @Test
    public void updateHarFileTest() throws IOException {
        Har har = generateHar();
        HarFile harFile = new HarFile(har.getLog().getBrowser().getName(), har.getLog().getVersion(), mapper.writeValueAsString(har));

        when(harRepository.findById(anyLong())).thenReturn(Optional.of(harFile));
        when(objectMapper.readValue(any(InputStream.class), eq(Har.class))).thenReturn(har);
        when(objectMapper.writeValueAsString(any(Har.class))).thenReturn(mapper.writeValueAsString(har));
        when(objectMapper.readValue(anyString(), eq(Har.class))).thenReturn(har);

        MockMultipartFile multipartFile = new MockMultipartFile("file", "update.har", MediaType.APPLICATION_JSON_VALUE, new byte[]{});
        Har byService = service.updateHarFile(randomLong(), multipartFile);

        assertEquals(har.getLog().getBrowser().getName(), byService.getLog().getBrowser().getName());
        assertEquals(har.getLog().getVersion(), byService.getLog().getVersion());
        assertEquals(new ObjectMapper().writeValueAsString(har), new ObjectMapper().writeValueAsString(har));

        when(harRepository.findById(5L)).thenThrow(new FileNotFoundException("File with id = " + 5 + " not found! Can not update"));
        Throwable thrown = catchThrowable(() -> {
            service.updateHarFile(5L, multipartFile);
        });

        assertEquals(thrown.getMessage(), "File with id = 5 not found! Can not update");

    }

    @Test
    public void saveModelTest() throws IOException {
        TestProfile testProfile = generateTestProfile();
        when(objectMapper.writeValueAsString(any(TestProfile.class))).thenReturn(mapper.writeValueAsString(testProfile));

        InnerModelData innerModelData = new InnerModelData((long) testProfile.getRequests().size(), mapper.writeValueAsString(testProfile));
        when(modelRepository.save(any(InnerModelData.class))).thenReturn(innerModelData);

        InnerModelData byService = service.saveModel(testProfile);

        assertEquals(byService.getCountRequest().longValue(), testProfile.getRequests().size());
        assertEquals(byService.getData(), mapper.writeValueAsString(testProfile));

    }

    @Test
    public void transformToInnerModelTest() throws IOException {
        Har har = generateHar();
        when(objectMapper.readValue(anyString(), eq(Har.class))).thenReturn(har);

        String content = mapper.writeValueAsString(har);
        TestProfile byService = service.transformToInnerModel(content);

        assertEquals(har.getLog().getEntries().size(), byService.getRequests().size());
    }

    private Har generateHar() {
        Har har = new Har();
        HarLog log = new HarLog();
        log.setVersion("1.5");

        HarBrowser browser = new HarBrowser();
        browser.setName("Firefox");

        log.setBrowser(browser);

        har.setLog(log);
        return har;
    }

    private Long randomLong() {
        return (long) (Math.random() * 100000 + 4);
    }

    private TestProfile generateTestProfile() {
        TestProfile testProfile = new TestProfile();

        List<Request> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add(generateRequest());
        }

        testProfile.setRequests(list);
        return testProfile;
    }

    private Request generateRequest() {
        String url = "/some/resource/" + randomLong();
        String body = UUID.randomUUID().toString();
        Map<String, String> headers = null;
        Map<String, String> params = null;
        HttpMethod method = HttpMethod.POST;

        return new Request(url, body, headers, params, method, 0.0);
    }
}
