package ua.glumaks.exceptions;

public class TelegramServiceException extends RuntimeException {

    public TelegramServiceException() {
    }

    public TelegramServiceException(String message) {
        super(message);
    }

    public TelegramServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramServiceException(Throwable cause) {
        super(cause);
    }

}
