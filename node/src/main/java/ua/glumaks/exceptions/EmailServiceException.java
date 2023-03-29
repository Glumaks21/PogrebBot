package ua.glumaks.exceptions;

public class EmailServiceException extends RuntimeException {

    public EmailServiceException() {
    }

    public EmailServiceException(String message) {
        super(message);
    }

    public EmailServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
