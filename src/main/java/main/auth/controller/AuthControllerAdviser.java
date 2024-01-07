package main.auth.controller;

import main.auth.exception.NotFoundUserException;
import main.blog.controller.dto.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdviser {

    @ExceptionHandler({
            NotFoundUserException.class,
    })
    public ResponseEntity<ErrorResponse> handleNotFoundData(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
