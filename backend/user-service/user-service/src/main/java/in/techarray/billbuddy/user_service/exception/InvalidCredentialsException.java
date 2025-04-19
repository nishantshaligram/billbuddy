package in.techarray.billbuddy.user_service.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException( String message ) {
        super( message );
    }
}
