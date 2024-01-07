package main.blog.exception;

public class NotFoundUserException extends RuntimeException {

    public NotFoundUserException(final String message) {
        super(message);
    }
}
