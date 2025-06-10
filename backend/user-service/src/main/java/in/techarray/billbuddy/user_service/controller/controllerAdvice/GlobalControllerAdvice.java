package in.techarray.billbuddy.user_service.controller.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import in.techarray.billbuddy.user_service.dto.ErrorResponseDTO;
import in.techarray.billbuddy.user_service.exception.InvalidCredentialsException;
import in.techarray.billbuddy.user_service.exception.InvalidTokenException;
import in.techarray.billbuddy.user_service.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler( value = UserNotFoundException.class )
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(404);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler( value = InvalidCredentialsException.class )
    public String handleInvalidCredentialsException(InvalidCredentialsException e) {
        // Log the exception or perform any other necessary actions
        return e.getMessage(); // Return a user-friendly message or error response
    }

    @ExceptionHandler( value = InvalidTokenException.class )
    public String handleInvalidTokenException(InvalidTokenException e) {
        // Log the exception or perform any other necessary actions
        return e.getMessage(); // Return a user-friendly message or error response
    }
}
