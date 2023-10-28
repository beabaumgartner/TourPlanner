package at.fhtw.exceptions;

// 500 - Internal Server Error
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }
}
