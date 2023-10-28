package at.fhtw.exceptions;

public class FailedToParseJsonFileException extends RuntimeException {
    public FailedToParseJsonFileException(String message) {
        super(message);
    }
    public FailedToParseJsonFileException(String message, Throwable cause) {
        super(message, cause);
    }
    public FailedToParseJsonFileException(Throwable cause) {
        super(cause);
    }
}
