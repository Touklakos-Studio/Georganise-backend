package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * This class represents the UserUpdateDTO data transfer object with all its properties.
 * It is used to transfer data for updating a user.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class UserUpdateDTO implements Serializable {

    /**
     * The new nickname of the user.
     */
    private String nickname;

    /**
     * The new password of the user.
     */
    private String password;

    /**
     * The new email of the user.
     */
    private String email;
}