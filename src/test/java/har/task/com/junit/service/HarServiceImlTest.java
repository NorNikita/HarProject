package har.task.com.junit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import har.task.com.entity.HarFile;
import har.task.com.mapper.model.Har;
import har.task.com.mapper.model.entry.HarBrowser;
import har.task.com.mapper.model.entry.HarLog;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        when(objectMapper.readValue(any(InputStream.class), any(Class.class))).thenReturn(har);
        when(objectMapper.writeValueAsString(any(Har.class))).thenReturn(mapper.writeValueAsString(har));

        HarLog log = har.getLog();
        when(harRepository.save(any(HarFile.class))).thenReturn(new HarFile(log.getBrowser().getName(), log.getVersion(), mapper.writeValueAsString(har)));
        MockMultipartFile multipartFile = new MockMultipartFile("file", "some.har", MediaType.APPLICATION_JSON_UTF8_VALUE, new byte[]{1,3});

        HarFile createByService = service.saveFile(multipartFile);

        assertTrue(log.getBrowser().getName().equals(createByService.getBrowser()));
        assertTrue(log.getVersion().equals(createByService.getVersion()));
        assertTrue(new ObjectMapper().writeValueAsString(har).equals(createByService.getContent()));
    }

    @Test
    public void getHarFileTest() throws IOException {
        Har har = generateHar();
        HarFile harFile = new HarFile(har.getLog().getBrowser().getName(), har.getLog().getVersion(), mapper.writeValueAsString(har));

        when(harRepository.findById(anyLong())).thenReturn(Optional.of(harFile));
        when(objectMapper.readValue(anyString(), any(Class.class))).thenReturn(har);

        Har getByService = service.getHarFile(anyLong());
        assertEquals(getByService.getLog().getBrowser().getName(), har.getLog().getBrowser().getName());
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
        return (long) (Math.random() * 100000);
    }
}
