package com.pflb.hartask.service;

import com.pflb.hartask.entity.HarFile;
import com.pflb.hartask.entity.InnerModelData;
import com.pflb.hartask.datamodel.innermodel.TestProfile;
import com.pflb.hartask.datamodel.harmodel.Har;
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
