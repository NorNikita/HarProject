package com.pflb.hartask.junit.controller;

import com.pflb.hartask.controller.HarController;
import com.pflb.hartask.datamodel.harmodel.Har;
import com.pflb.hartask.datamodel.harmodel.entry.HttpMethod;
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
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HarControllerTest{

    @Mock
    private IHarService service;

    @InjectMocks
    private static HarController harController;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(harController).build();
    }

    @Test
    void getFileTest() throws Exception {
        when(service.getHarFile(anyLong())).thenReturn(new Har());

        MvcResult result = mockMvc.perform(get("/getFile/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, result.getResponse().getContentType());
    }

    @Test
    void deleteFileTest() throws Exception {
        doNothing().when(service).deleteHarFile(anyLong());

        MvcResult result = mockMvc.perform(delete("/delete/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void updateFileTest() throws Exception {
        when(service.updateHarFile(anyLong(), any(MultipartFile.class))).thenReturn(new Har());
        MockMultipartFile file = new MockMultipartFile("file", "some.har", MediaType.APPLICATION_JSON_VALUE, new byte[]{});

        MockMultipartHttpServletRequestBuilder builder = multipart("/update/{id}", 1927L);
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
