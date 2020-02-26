package com.pflb.hartask.junit.args.provider;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class MultipartFileSource {

    public static MockMultipartFile getMultipartFile() {
        return new MockMultipartFile("file", "update.har", MediaType.APPLICATION_JSON_VALUE, new byte[]{});
    }
}
