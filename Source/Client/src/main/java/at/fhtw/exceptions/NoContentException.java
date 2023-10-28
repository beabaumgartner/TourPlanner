package at.fhtw.exceptions;

// 204 - No Content
public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoContentException(Throwable cause) {
        super(cause);
    }
}
