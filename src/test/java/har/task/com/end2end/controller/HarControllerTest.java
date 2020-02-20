package har.task.com.end2end.controller;

import har.task.com.datamodel.harmodel.entry.HttpMethod;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application.yml")
@AutoConfigureMockMvc
@Sql(value = "/db/add-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/db/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class HarControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Value("${server.port}")
    protected String port;

    @Test
    void getFileTest() throws Exception {
        MvcResult result = mockMvc.perform(get("http://localhost:" + port + "getFile/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getStatus(), 200);
    }

    @ParameterizedTest
    @MethodSource("har.task.com.end2end.controller.HarFileSource#fileSource")
    void updateFileTest(MockMultipartFile file) throws Exception {

        MockMultipartHttpServletRequestBuilder builder = multipart("/update/{id}", 1L);
        builder.with((request) -> {
            request.setMethod(HttpMethod.PUT.name());
            return request;
        });

        MvcResult result = mockMvc.perform(builder.file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getStatus(), 200);
    }

    @Test
    void deleteFileTest() throws Exception {
        MvcResult result = mockMvc.perform(delete("/delete/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getStatus(), 200);
    }

}
