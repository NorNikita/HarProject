package har.task.com.controller;

import har.task.com.mapper.model.Har;
import har.task.com.service.IHarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class HarController {

    private IHarService service;

    @Autowired
    public HarController(IHarService service) {
        this.service = service;
    }

    @GetMapping("/getFile/{id}")
    public ResponseEntity<Har> getFile(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(service.getHarFile(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFile(@PathVariable Long id) {
        service.deleteHarFile(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Har> updateFile(@PathVariable Long id, @RequestBody MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(service.updateHarFile(id, multipartFile));
    }
}