package com.pflb.hartask.junit.service;

import com.pflb.hartask.model.harmodel.Har;
import com.pflb.hartask.model.innermodel.TestProfile;
import com.pflb.hartask.model.entity.HarFile;
import com.pflb.hartask.model.entity.InnerModelData;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.stream.Stream;

import static com.pflb.hartask.junit.service.DataGenerator.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HarServiceTestData {

    static Stream<Arguments> harStream() {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "update.har", MediaType.APPLICATION_JSON_VALUE, new byte[]{});

        Har har1 = generateHar();
        Har har2 = generateHar();
        Har har3 = generateHar();

        HarFile harFile1 = getHarFile(har1);
        HarFile harFile2 = getHarFile(har2);
        HarFile harFile3 = getHarFile(har3);

        return Stream.of(
                arguments(har1, harFile1, multipartFile, randomLong()),
                arguments(har2, harFile2, multipartFile, randomLong()),
                arguments(har3, harFile3, multipartFile, randomLong())
        );
    }

    static Stream<Arguments> testProfileStream() {
        TestProfile testProfile = getTestProfile();
        InnerModelData innerModelData = getInnerModelData(testProfile);

        return Stream.of(
                arguments(testProfile, innerModelData)
        );
    }


}