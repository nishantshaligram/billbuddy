package in.techarray.billbuddy.user_service.exception;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException(String message){
        super(message);
    }
}
