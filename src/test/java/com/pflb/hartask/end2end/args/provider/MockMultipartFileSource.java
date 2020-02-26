package com.pflb.hartask.end2end.args.provider;

import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

public class MockMultipartFileSource {

    public static MockMultipartFile getFile() {
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("ru.wiktionary.org.har");
        MockMultipartFile file = null;

        try {
            file = new MockMultipartFile("file", systemResourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
