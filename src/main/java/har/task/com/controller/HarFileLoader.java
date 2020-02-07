package har.task.com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class HarFileLoader {

    @PostMapping("/load")
    public ResponseEntity loadFile(@RequestParam(name = "file")MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok().build();
    }
}

