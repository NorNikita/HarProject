package har.task.com.controller;

import har.task.com.entity.HarFile;
import har.task.com.service.IHarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class HarFileLoader {

    private IHarService service;

    @Autowired
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

