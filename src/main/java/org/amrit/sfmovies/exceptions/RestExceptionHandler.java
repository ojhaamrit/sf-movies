package org.amrit.sfmovies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiException> handleException(Exception exception) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiException> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage(), illegalArgumentException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiException> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, resourceNotFoundException.getMessage(), resourceNotFoundException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ApiException> handleBadRequestException(BadRequestException badRequestException) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, badRequestException.getMessage(), badRequestException);
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<ApiException> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }

}
