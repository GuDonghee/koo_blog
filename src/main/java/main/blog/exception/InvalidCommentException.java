package main.blog.exception;

public class InvalidCommentException extends RuntimeException {

    public InvalidCommentException(final String message) {
        super(message);
    }
}
