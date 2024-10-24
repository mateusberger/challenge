package br.com.mateusberger.challenge.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        return error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FailToValidateAuthenticationException.class)
    public ResponseEntity<DefaultErrorResponse> handleFailToValidateAuthenticationException(Exception ex, WebRequest request) {
        return error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidInformationException.class)
    public ResponseEntity<DefaultErrorResponse> handleInvalidInformationException(Exception ex, WebRequest request) {
        return error("Invalid information was requested", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoContentFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleNoContentFoundException(Exception ex, WebRequest request) {
        return error("No content was found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentAlreadyProcessedException.class)
    public ResponseEntity<DefaultErrorResponse> handlePaymentAlreadyProcessedException(Exception ex, WebRequest request) {
        return error("Payment was already processed", HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<DefaultErrorResponse> handleUnauthorizedUserException(Exception ex, WebRequest request) {
        return error("Authenticated user don't have permission", HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<DefaultErrorResponse> error(String message, HttpStatus status){
        return new ResponseEntity<>(new DefaultErrorResponse(status.value(), message), status);
    }
}
