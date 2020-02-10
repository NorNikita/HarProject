package har.task.com.controller;

import har.task.com.service.HarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class HarFileLoader {

    private HarService service;

    @Autowired
    public HarFileLoader(HarService service) {
        this.service = service;
    }

    @PostMapping("/load")
    public ResponseEntity loadFile(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        service.saveFile(multipartFile);
        return ResponseEntity.ok().build();
    }
}

