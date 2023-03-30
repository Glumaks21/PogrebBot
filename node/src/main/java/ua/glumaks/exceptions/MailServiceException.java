package ua.glumaks.exceptions;

public class MailServiceException extends RuntimeException {

    public MailServiceException() {
    }

    public MailServiceException(String message) {
        super(message);
    }

    public MailServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailServiceException(Throwable cause) {
        super(cause);
    }

}
