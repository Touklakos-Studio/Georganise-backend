package isima.georganise.app.exception;

/**
 * This class represents the ConflictException.
 * It extends the RuntimeException and is thrown when a conflict occurs.
 */
public class ConflictException extends RuntimeException {

    /**
     * Default constructor for the ConflictException.
     * It sets the message to "Conflict".
     */
    public ConflictException() {
        super("Conflict");
    }

    /**
     * Constructor for the ConflictException.
     * It takes a custom message as a parameter.
     *
     * @param message The custom message for the exception.
     */
    public ConflictException(String message) {
        super(message);
    }
}