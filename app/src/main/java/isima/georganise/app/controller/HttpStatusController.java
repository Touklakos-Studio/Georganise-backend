package isima.georganise.app.controller;

import isima.georganise.app.entity.dto.RealtimeConflictDTO;
import isima.georganise.app.exception.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is responsible for handling exceptions globally across the application.
 * It uses the @RestControllerAdvice annotation to provide centralized exception handling across all @RequestMapping methods.
 */
@RestControllerAdvice
public class HttpStatusController {

    private static final String MESSAGE_ERROR = "Error: ";

    /**
     * Handles generic Exception.class
     * @param ex the exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "Something went very wrong")
    public void handleUnknownError(@NotNull Exception ex) {
        System.err.println(MESSAGE_ERROR + "Unexpected error: " + ex.getMessage());
    }

    /**
     * Handles NotFoundException
     * @param ex the exception
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found")
    public void handleNotFoundError(@NotNull RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + "No corresponding entity found: " + ex.getMessage());
    }

    /**
     * Handles IllegalArgumentException
     * @param ex the exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request")
    public void handleBadRequestError(@NotNull RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + "A parameter was missing, not expected or badly used: " + ex.getMessage());
    }

    /**
     * Handles WrongPasswordException
     * @param ex the exception
     */
    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong password")
    public void handleWrongPasswordError(@NotNull RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + "The wrong password was given: " + ex.getMessage());
    }

    /**
     * Handles UnimplementedException
     * @param ex the exception
     */
    @ExceptionHandler(UnimplementedException.class)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Not implemented")
    public void handleNotImplementedError(@NotNull RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + "This functionality is implemented yet: " + ex.getMessage());
    }

    /**
     * Handles NotLoggedException
     * @param ex the exception
     */
    @ExceptionHandler(NotLoggedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Not logged")
    public void handleNotLoggedError(@NotNull RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + "The requester is not logged in: " + ex.getMessage());
    }

    /**
     * Handles UnauthorizedException
     * @param ex the exception
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
    public void handleUnauthorizedError(@NotNull RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + "The requester does not have the authorization to do this action: " + ex.getMessage());
    }

    /**
     * Handles MissingRequestCookieException
     * @param ex the exception
     */
    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Not logged")
    public void handleMissingRequestCookieError(@NotNull Exception ex) {
        System.err.println(MESSAGE_ERROR + "The authToken cookie is missing: " + ex.getMessage());
    }

    /**
     * Handles ConflictException
     * @param ex the exception
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Conflict")
    public void handleConflictError(@NotNull RuntimeException ex) {
        System.err.println(MESSAGE_ERROR + "There is a conflict with an existing entity: " + ex.getMessage());
    }

    /**
     * Handles AlreadyCreatedException
     * @param ex the exception
     * @return ResponseEntity with the conflict details
     */
    @ExceptionHandler(AlreadyCreatedException.class)
    @ResponseBody
    public @NotNull ResponseEntity<RealtimeConflictDTO> handleAlreadyCreatedError(@NotNull AlreadyCreatedException ex) {
        System.err.println(MESSAGE_ERROR + "This place already exist: " + ex.getMessage());
        return new ResponseEntity<>(new RealtimeConflictDTO(ex.getId()), HttpStatus.CONFLICT);
    }
}