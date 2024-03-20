package isima.georganise.app.exception;

/**
 * This class represents the UnauthorizedException.
 * It extends the RuntimeException and is thrown when a user is not authorized to perform a certain action.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Default constructor for the UnauthorizedException.
     * It sets the message to "Unauthorized".
     */
    public UnauthorizedException() {
        super("Unauthorized");
    }

    /**
     * Constructor for the UnauthorizedException.
     * It takes a username and an action as parameters.
     * The message is set to "User [username] is not authorized to [action]".
     *
     * @param userName The username of the user who is not authorized.
     * @param action The action the user is not authorized to perform.
     */
    public UnauthorizedException(String userName, String action) {
        super("User " + userName + " is not authorized to " + action);
    }
}