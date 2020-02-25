package com.pflb.hartask.junit.controller;

import com.pflb.hartask.controller.HarFileLoader;
import com.pflb.hartask.entity.HarFile;
import com.pflb.hartask.service.IHarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class HarFileLoaderTest {
    private MockMvc mockMvc;

    @Mock
    protected IHarService service;

    @InjectMocks
    private HarFileLoader loader;

    @BeforeEach
    void init() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(loader).build();
        when(service.saveFile(any(MultipartFile.class))).thenReturn(new HarFile());
        doNothing().when(service).sendContentInQueue(any(HarFile.class));
    }

    @Test
    void loadFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "some.har", MediaType.APPLICATION_JSON_VALUE, new byte[]{});

        MvcResult result = mockMvc.perform(multipart("/load")
                .file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

}
