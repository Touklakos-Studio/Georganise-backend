package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * This class represents the UserCreationDTO data transfer object with all its properties.
 * It is used to transfer data for creating a user.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class UserCreationDTO implements Serializable {

    /**
     * The nickname of the user.
     */
    private String nickname;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The email of the user.
     */
    private String email;
}