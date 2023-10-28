package at.fhtw.exceptions;

public class FailedToSendRequestException extends RuntimeException {
    public FailedToSendRequestException(String message) {
        super(message);
    }
    public FailedToSendRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public FailedToSendRequestException(Throwable cause) {
        super(cause);
    }
}
