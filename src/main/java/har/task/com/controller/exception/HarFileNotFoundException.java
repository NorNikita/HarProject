package har.task.com.controller.exception;

public class HarFileNotFoundException extends RuntimeException {

    public HarFileNotFoundException(String message) {
        super(message);
    }
}
