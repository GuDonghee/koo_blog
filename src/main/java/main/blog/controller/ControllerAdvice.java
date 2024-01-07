package main.blog.controller;

import main.blog.controller.dto.error.ErrorResponse;
import main.blog.exception.DuplicateUserException;
import main.blog.exception.InvalidPostException;
import main.blog.exception.InvalidUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            InvalidPostException.class,
            InvalidUserException.class,
            DuplicateUserException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidData(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidDtoField(final MethodArgumentNotValidException e) {
        FieldError firstFieldError = e.getFieldErrors().get(0);
        ErrorResponse errorResponse = new ErrorResponse(firstFieldError.getDefaultMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
