package har.task.com.end2end.controller;

import har.task.com.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("/application.yml")
class HarFileLoaderTest {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${some}")
    private String some;

    @Test
    void someTest() {
    }
}
