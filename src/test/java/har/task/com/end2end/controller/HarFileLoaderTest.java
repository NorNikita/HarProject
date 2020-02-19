package har.task.com.end2end.controller;

import har.task.com.listener.RabbitMQListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application.yml")
class HarFileLoaderTest {

    private MockMvc mockMvc;

    @Value("${server.port}")
    private String port;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RabbitMQListener rabbitMQListener;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void saveFileTest() throws Exception {
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("ru.wiktionary.org.har");
        MockMultipartFile file = new MockMultipartFile("file", systemResourceAsStream);

        MvcResult result = mockMvc.perform(multipart("http://localhost:" + port + "/load").file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getStatus(), 200);
    }

}
