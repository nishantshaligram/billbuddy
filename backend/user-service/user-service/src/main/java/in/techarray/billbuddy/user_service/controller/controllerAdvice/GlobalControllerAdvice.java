package in.techarray.billbuddy.user_service.controller.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import in.techarray.billbuddy.user_service.dto.ErrorResponseDTO;
import in.techarray.billbuddy.user_service.exception.ValidationException;

@ControllerAdvice
public class GlobalControllerAdvice {
    
    public ResponseEntity<ErrorResponseDTO> handleValidationException( ValidationException validationException ) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO( validationException.getMessage(), 400 );
        return new ResponseEntity<>( errorResponseDTO, HttpStatus.BAD_REQUEST );
    }
}
