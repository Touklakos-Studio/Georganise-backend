package isima.georganise.app.exception;

/**
 * This class represents the NotFoundException.
 * It extends the RuntimeException and is thrown when a resource is not found.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Default constructor for the NotFoundException.
     * It sets the message to "Resource not found.".
     */
    public NotFoundException() {
        super("Resource not found.");
    }

    /**
     * Constructor for the NotFoundException.
     * It takes a custom message as a parameter.
     *
     * @param message The custom message for the exception.
     */
    public NotFoundException(String message) {
        super(message);
    }
}