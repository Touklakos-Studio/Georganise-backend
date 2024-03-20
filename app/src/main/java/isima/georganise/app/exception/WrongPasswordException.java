package isima.georganise.app.exception;

/**
 * This class represents the WrongPasswordException.
 * It extends the RuntimeException and is thrown when a wrong password is entered.
 */
public class WrongPasswordException extends RuntimeException {

    /**
     * Default constructor for the WrongPasswordException.
     * It sets the message to "Wrong password.".
     */
    public WrongPasswordException() {
        super("Wrong password.");
    }

    /**
     * Constructor for the WrongPasswordException.
     * It takes a custom message as a parameter and appends "Wrong password for: " to it.
     *
     * @param message The custom message for the exception.
     */
    public WrongPasswordException(String message) {
        super("Wrong password for: " + message);
    }
}