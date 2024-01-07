package blog.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(final String message) {
        super(message);
    }
}
