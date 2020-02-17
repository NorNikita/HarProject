package har.task.com.junit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import har.task.com.controller.exception.HarFileNotFoundException;
import har.task.com.datamodel.harmodel.Har;
import har.task.com.datamodel.innermodel.TestProfile;
import har.task.com.entity.HarFile;
import har.task.com.entity.InnerModelData;
import har.task.com.repository.HarFileRepository;
import har.task.com.repository.InnerModelDataRepository;
import har.task.com.service.impl.HarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HarServiceImlTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HarFileRepository harRepository;

    @Mock
    private InnerModelDataRepository modelRepository;

    @InjectMocks
    private HarServiceImpl service;

    @ParameterizedTest()
    @MethodSource("har.task.com.junit.service.HarServiceTestData#harStream")
    void saveFileTest(Har har, HarFile file, MultipartFile multipartFile) throws IOException {
        when(objectMapper.readValue(any(InputStream.class), eq(Har.class))).thenReturn(har);
        when(objectMapper.writeValueAsString(any(Har.class))).thenReturn(mapper.writeValueAsString(har));
        when(harRepository.save(any(HarFile.class))).thenReturn(file);

        HarFile byService = service.saveFile(multipartFile);

        assertEquals(mapper.writeValueAsString(har), byService.getContent());
    }

    @ParameterizedTest
    @MethodSource("har.task.com.junit.service.HarServiceTestData#harStream")
    void getHarFileTest(Har har, HarFile file, MultipartFile multipartFile, Long id) throws IOException {
        when(harRepository.findById(anyLong())).thenReturn(Optional.of(file));
        when(objectMapper.readValue(anyString(), eq(Har.class))).thenReturn(har);

        Har byService = service.getHarFile(anyLong());
        assertEquals(mapper.writeValueAsString(har), mapper.writeValueAsString(byService));

        when(harRepository.findById(id)).thenThrow(new HarFileNotFoundException("File with id = " + id + " not found"));
        Assertions.assertThrows(HarFileNotFoundException.class, () ->  service.getHarFile(id),"File with id = " + id + " not found");
    }

    @ParameterizedTest
    @MethodSource({"har.task.com.junit.service.HarServiceTestData#harStream"})
    void updateHarFileTest(Har har, HarFile file, MultipartFile multipartFile, Long id) throws IOException {
        when(harRepository.findById(anyLong())).thenReturn(Optional.of(file));
        when(objectMapper.readValue(any(InputStream.class), eq(Har.class))).thenReturn(har);
        when(objectMapper.writeValueAsString(any(Har.class))).thenReturn(mapper.writeValueAsString(har));
        when(objectMapper.readValue(anyString(), eq(Har.class))).thenReturn(har);

        Har byService = service.updateHarFile(anyLong(), multipartFile);
        assertEquals(mapper.writeValueAsString(har), mapper.writeValueAsString(byService));

        when(harRepository.findById(id)).thenThrow(new HarFileNotFoundException("File with id = " + id + " not found"));
        Assertions.assertThrows(HarFileNotFoundException.class, () ->  service.getHarFile(id),"File with id = " + id + " not found");
    }

    @ParameterizedTest
    @MethodSource("har.task.com.junit.service.HarServiceTestData#testProfileStream")
    void saveModelTest(TestProfile testProfile, InnerModelData innerModelData) throws IOException {
        when(objectMapper.writeValueAsString(any(TestProfile.class))).thenReturn(mapper.writeValueAsString(testProfile));
        when(modelRepository.save(any(InnerModelData.class))).thenReturn(innerModelData);

        InnerModelData byService = service.saveModel(testProfile);

        assertEquals(byService.getCountRequest().longValue(), testProfile.getRequests().size());
        assertEquals(byService.getData(), mapper.writeValueAsString(testProfile));
    }

    @ParameterizedTest
    @MethodSource("har.task.com.junit.service.HarServiceTestData#harStream")
    void transformToInnerModelTest(Har har) throws IOException {
        when(objectMapper.readValue(anyString(), eq(Har.class))).thenReturn(har);

        TestProfile byService = service.transformToInnerModel(mapper.writeValueAsString(har));

        assertEquals(har.getLog().getEntries().size(), byService.getRequests().size());
    }
}
