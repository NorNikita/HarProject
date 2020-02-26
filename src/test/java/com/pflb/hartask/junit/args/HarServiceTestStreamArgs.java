package com.pflb.hartask.junit.args;

import com.pflb.hartask.junit.args.provider.*;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HarServiceTestStreamArgs {

    static Stream<Arguments> harStream() {

        return Stream.of(
                arguments(HarSource.getHar(),
                        HarFileSource.getHarFile(HarSource.getHar()),
                        MultipartFileSource.getMultipartFile(),
                        RandomLongSource.randomLong()),

                arguments(HarSource.getHar(),
                        HarFileSource.getHarFile(HarSource.getHar()),
                        MultipartFileSource.getMultipartFile(),
                        RandomLongSource.randomLong())
        );
    }

    static Stream<Arguments> testProfileStream() {

        return Stream.of(
                arguments(TestProfileSource.getTestProfile(),
                        InnerModelDataSource.getInnerModelData(TestProfileSource.getTestProfile())),
                arguments(TestProfileSource.getTestProfile(),
                        InnerModelDataSource.getInnerModelData(TestProfileSource.getTestProfile()))

        );
    }
}
