package blog.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(final String message) {
        super(message);
    }
}
