package isima.georganise.app.exception;

/**
 * This class represents the NotLoggedException.
 * It extends the RuntimeException and is thrown when a user is not logged in.
 */
public class NotLoggedException extends RuntimeException {

    /**
     * Default constructor for the NotLoggedException.
     * It sets the message to "User is not logged in".
     */
    public NotLoggedException() {
        super("User is not logged in");
    }

    /**
     * Constructor for the NotLoggedException.
     * It takes a custom message as a parameter and appends " User is not logged in" to it.
     *
     * @param message The custom message for the exception.
     */
    public NotLoggedException(String message) {
        super(message + " User is not logged in");
    }
}