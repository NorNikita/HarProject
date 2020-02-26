package com.pflb.hartask.end2end.controller;

import com.pflb.hartask.listener.RabbitMQListener;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(value = "/db/add-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/db/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class HarFileLoaderTest {

    @Autowired
    protected MockMvc mockMvc;

    @Value("${server.port}")
    protected String port;

    @Autowired
    private RabbitMQListener rabbitMQListener;

    @ParameterizedTest
    @MethodSource("com.pflb.hartask.end2end.controller.HarFileSource#fileSource")
    void saveFileTest(MockMultipartFile file) throws Exception {
        rabbitMQListener.initCounter();

        MvcResult result = mockMvc.perform(multipart("http://localhost:" + port + "/load").file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Thread.sleep(1000);

        assertEquals(result.getResponse().getStatus(), 200);
        assertEquals(1, rabbitMQListener.getCounter());
    }

}
