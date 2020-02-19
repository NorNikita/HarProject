package har.task.com.end2end.controller;

import har.task.com.end2end.BaseControllerTest;
import har.task.com.listener.RabbitMQListener;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HarFileLoaderTest extends BaseControllerTest {

    @Autowired
    private RabbitMQListener rabbitMQListener;

    @ParameterizedTest
    @MethodSource("har.task.com.end2end.controller.HarFileSource#fileSource")
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
