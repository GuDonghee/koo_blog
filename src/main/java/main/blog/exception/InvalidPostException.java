package main.blog.exception;

public class InvalidPostException extends RuntimeException {

    public InvalidPostException(final String message) {
        super(message);
    }
}
