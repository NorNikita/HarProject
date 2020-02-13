package har.task.com.junit.controller;

import har.task.com.controller.HarFileLoader;
import har.task.com.entity.HarFile;
import har.task.com.service.IHarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class HarFileLoaderTest {
    private MockMvc mockMvc;

    @Mock
    protected IHarService service;

    @InjectMocks
    private HarFileLoader loader;

    @Before
    public void init() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(loader).build();
        when(service.saveFile(any(MultipartFile.class))).thenReturn(new HarFile());
        doNothing().when(service).sendContentInQueue(any(HarFile.class));
    }

    @Test
    public void loadFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "some.har", MediaType.APPLICATION_JSON_UTF8_VALUE, RandomUtils.randomContent());

        MvcResult result = mockMvc.perform(multipart("/load")
                .file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

}
