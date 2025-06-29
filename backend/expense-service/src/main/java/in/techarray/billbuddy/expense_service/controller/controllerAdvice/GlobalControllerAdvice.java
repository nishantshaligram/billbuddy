package in.techarray.billbuddy.expense_service.controller.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import in.techarray.billbuddy.expense_service.dto.ErrorResponseDto;
import in.techarray.billbuddy.expense_service.exception.ExpenseNotFoundException;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler( value = ExpenseNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleExpenseNotFoundException(ExpenseNotFoundException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(404);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
