package har.task.com.controller.handler;

import har.task.com.controller.exception.HarFileNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "har.task.com.controller")
public class HandlerException {

    @ExceptionHandler(HarFileNotFoundException.class)
    public ResponseEntity<ExceptionFormat> notFound(HarFileNotFoundException exc) {
        return new ResponseEntity<>(new ExceptionFormat(exc.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionFormat> internalError(Exception exc) {
        return new ResponseEntity<>(new ExceptionFormat(exc.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @AllArgsConstructor
    private static class ExceptionFormat {
        private String message;
    }

}
