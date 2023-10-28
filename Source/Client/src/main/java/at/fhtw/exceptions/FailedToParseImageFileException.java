package at.fhtw.exceptions;

public class FailedToParseImageFileException extends RuntimeException {
    public FailedToParseImageFileException(String message) {
        super(message);
    }
    public FailedToParseImageFileException(String message, Throwable cause) {
        super(message, cause);
    }
    public FailedToParseImageFileException(Throwable cause) {
        super(cause);
    }
}
