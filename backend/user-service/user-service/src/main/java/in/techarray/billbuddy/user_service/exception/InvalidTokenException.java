package in.techarray.billbuddy.user_service.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
    }
    public InvalidTokenException(String message) {
        super(message);
    }
}
