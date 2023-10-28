package at.fhtw.tourplanner.exceptions;

// 400 - Bad Request
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
