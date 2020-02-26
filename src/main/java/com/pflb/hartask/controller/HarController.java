package com.pflb.hartask.controller;

import com.pflb.hartask.datamodel.harmodel.Har;
import com.pflb.hartask.service.IHarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class HarController {

    private IHarService service;

    public HarController(IHarService service) {
        this.service = service;
    }

    @GetMapping("/check")
    public ResponseEntity check() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getFile/{id}")
    public ResponseEntity<Har> getFile(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(service.getHarFile(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFile(@PathVariable Long id) {
        service.deleteHarFile(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Har> updateFile(@PathVariable Long id, @RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(service.updateHarFile(id, multipartFile));
    }
}