package com.pflb.hartask.service;

import com.pflb.hartask.model.entity.HarFile;
import com.pflb.hartask.model.entity.InnerModelData;
import com.pflb.hartask.model.innermodel.TestProfile;
import com.pflb.hartask.model.harmodel.Har;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IHarService {

    HarFile saveFile(MultipartFile file) throws IOException;

    void sendContentInQueue(HarFile entity);

    Har getHarFile(Long id) throws IOException;

    void deleteHarFile(Long id);

    Har updateHarFile(Long id, MultipartFile multipartFile) throws IOException;

    InnerModelData saveModel(TestProfile testProfile) throws IOException;

    TestProfile transformToInnerModel(String content) throws IOException;
}
