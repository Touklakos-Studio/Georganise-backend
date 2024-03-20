package isima.georganise.app.exception;

/**
 * This class represents the UnimplementedException.
 * It extends the RuntimeException and is thrown when a feature or method is not yet implemented.
 */
public class UnimplementedException extends RuntimeException {

    /**
     * Default constructor for the UnimplementedException.
     * It calls the parent constructor without a message.
     */
    public UnimplementedException() {
        super();
    }

    /**
     * Constructor for the UnimplementedException.
     * It takes a custom message as a parameter.
     *
     * @param message The custom message for the exception.
     */
    public UnimplementedException(String message) {
        super(message);
    }
}