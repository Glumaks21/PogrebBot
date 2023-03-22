package ua.glumaks.exceptions;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundException(Throwable cause) {
        super(cause);
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}
