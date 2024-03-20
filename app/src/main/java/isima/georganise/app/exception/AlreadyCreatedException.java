package isima.georganise.app.exception;

import lombok.Getter;

/**
 * This class represents the AlreadyCreatedException.
 * It extends the RuntimeException and is thrown when an entity is already created.
 * Lombok's @Getter annotation is used to automatically generate the getter methods.
 */
@Getter
public class AlreadyCreatedException extends RuntimeException {

    /**
     * The id of the entity that is already created.
     */
    private final Long id;

    /**
     * Constructor for the AlreadyCreatedException.
     * It takes an id as a parameter and sets the message to "Entity already created".
     *
     * @param id The id of the entity that is already created.
     */
    public AlreadyCreatedException(Long id) {
        super("Entity already created");
        this.id = id;
    }

    /**
     * Constructor for the AlreadyCreatedException.
     * It takes an id and a message as parameters.
     *
     * @param id The id of the entity that is already created.
     * @param message The custom message for the exception.
     */
    public AlreadyCreatedException(Long id, String message) {
        super(message);
        this.id = id;
    }
}