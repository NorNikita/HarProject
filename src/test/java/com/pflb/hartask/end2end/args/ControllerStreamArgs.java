package com.pflb.hartask.end2end.args;

import com.pflb.hartask.end2end.args.provider.MockMultipartFileSource;
import org.springframework.mock.web.MockMultipartFile;

import java.util.stream.Stream;

public class ControllerStreamArgs {

    public static Stream<MockMultipartFile> fileStream() {
        return Stream.of(MockMultipartFileSource.getFile());
    }
}
