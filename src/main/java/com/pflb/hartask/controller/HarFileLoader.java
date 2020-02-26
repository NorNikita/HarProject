package com.pflb.hartask.controller;

import com.pflb.hartask.entity.HarFile;
import com.pflb.hartask.service.IHarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class HarFileLoader {

    private IHarService service;

    public HarFileLoader(IHarService service) {
        this.service = service;
    }

    @PostMapping("/load")
    public ResponseEntity loadFile(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        HarFile harFile = service.saveFile(multipartFile);
        service.sendContentInQueue(harFile);
        return ResponseEntity.ok().build();
    }
}

