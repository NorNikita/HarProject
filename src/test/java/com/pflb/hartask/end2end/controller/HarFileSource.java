package com.pflb.hartask.end2end.controller;

import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

public class HarFileSource {

    static Stream<MockMultipartFile> fileSource() {
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("ru.wiktionary.org.har");
        MockMultipartFile file = null;

        try {
            file = new MockMultipartFile("file", systemResourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Stream.of(file);
    }
}
