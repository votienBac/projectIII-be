package vn.noron.apiconfig.config.exception;

public class InValidException extends RuntimeException {
    public static final int CODE = 422;
    public static final String DEFAULT_MESSAGE = "Your request is inValid";

    public InValidException(String message) {
        super(message);
    }

    public InValidException() {
        super(DEFAULT_MESSAGE);
    }
}
