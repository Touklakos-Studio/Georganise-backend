package isima.georganise.app.entity.util;

/**
 * This enum represents the different types of rights a user can have in the system.
 * It is used to define the access level of a user.
 */
public enum Right {

    /**
     * The WRITER right allows the user to create, read, update, and delete data.
     */
    WRITER,

    /**
     * The READER right allows the user to only read data.
     */
    READER
}