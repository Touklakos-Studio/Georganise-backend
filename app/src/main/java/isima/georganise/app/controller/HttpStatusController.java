package isima.georganise.app.controller;

import isima.georganise.app.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpStatusController {

    private static final String MESSAGE_ERROR = "Error: ";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "Something went very wrong")
    public void handleUnknownError(Exception ex) {
        System.err.println(MESSAGE_ERROR + ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found")
    public void handleNotFoundError(RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request")
    public void handleBadRequestError(RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + ex.getMessage());
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong password")
    public void handleWrongPasswordError(RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + ex.getMessage());
    }

    @ExceptionHandler(UnimplementedException.class)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Not implemented")
    public void handleNotImplementedError(RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + ex.getMessage());
    }

    @ExceptionHandler(NotLoggedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Not logged")
    public void handleNotLoggedError(RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
    public void handleUnauthorizedError(RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + ex.getMessage());
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Not logged")
    public void handleMissingRequestCookieError(Exception ex) {
        System.err.println(MESSAGE_ERROR + ex.getMessage());
    }
}