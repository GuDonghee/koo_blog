package main.blog.exception;

public class NotFoundPostException extends RuntimeException {

    public NotFoundPostException(final String message) {
        super(message);
    }
}
