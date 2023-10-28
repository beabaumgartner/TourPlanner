package at.fhtw.exceptions;

public class FailedToCreatePdfFileException extends RuntimeException {
    public FailedToCreatePdfFileException(String message) {
        super(message);
    }
    public FailedToCreatePdfFileException(String message, Throwable cause) {
        super(message, cause);
    }
    public FailedToCreatePdfFileException(Throwable cause) {
        super(cause);
    }
}
