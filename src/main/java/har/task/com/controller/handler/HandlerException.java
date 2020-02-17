package har.task.com.controller.handler;

import har.task.com.controller.exception.HarFileNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice(basePackages = "har.task.com.controller")
public class HandlerException {

    @ResponseBody
    @ExceptionHandler(HarFileNotFoundException.class)
    public ResponseEntity<ExceptionFormat> notFound(HarFileNotFoundException exc) {
        return new ResponseEntity<>(new ExceptionFormat(exc.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionFormat> internalError(IOException exc) {
        return new ResponseEntity<>(new ExceptionFormat(exc.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @AllArgsConstructor
    private static class ExceptionFormat {
        private String message;
    }

}
