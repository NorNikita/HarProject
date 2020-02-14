package har.task.com.junit.controller;

import har.task.com.controller.HarController;
import har.task.com.mapper.model.Har;
import har.task.com.mapper.model.entry.HttpMethod;
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
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class HarControllerTest{

    @Mock
    private IHarService service;

    @InjectMocks
    private static HarController harController;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(harController).build();
    }

    @Test
    public void getFileTest() throws Exception {
        when(service.getHarFile(anyLong())).thenReturn(new Har());

        MvcResult result = mockMvc.perform(get("/getFile/{id}", RandomUtils.randomLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, result.getResponse().getContentType());
    }

    @Test
    public void deleteFileTest() throws Exception {
        doNothing().when(service).deleteHarFile(anyLong());

        MvcResult result = mockMvc.perform(delete("/delete/{id}", RandomUtils.randomLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void updateFileTest() throws Exception {
        when(service.updateHarFile(anyLong(), any(MultipartFile.class))).thenReturn(new Har());
        MockMultipartFile file = new MockMultipartFile("file", "some.har", MediaType.APPLICATION_JSON_VALUE, RandomUtils.randomContent());

        MockMultipartHttpServletRequestBuilder builder = multipart("/update/{id}", RandomUtils.randomLong());
        builder.with((request) -> {
            request.setMethod(HttpMethod.PUT.name());
            return request;
        });

        MvcResult result = mockMvc.perform(builder.file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, result.getResponse().getContentType());
    }

}
