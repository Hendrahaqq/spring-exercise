package com.hendra.beexcersice.ErrorResponse;

import com.hendra.beexcersice.Entity.CustomErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException ex, WebRequest request) {
        String error = "Invalid request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse errorResponse = new CustomErrorResponse(status.value(), status.getReasonPhrase(), error);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleInternalError(Exception ex, WebRequest request) {
        String error = "Internal server error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        CustomErrorResponse errorResponse = new CustomErrorResponse(status.value(), status.getReasonPhrase(), error);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }
}
