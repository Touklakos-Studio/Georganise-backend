package isima.georganise.app.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * This class represents the GetUserNicknameDTO data transfer object with all its properties.
 * It is used to transfer data for getting a user's nickname.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 */
@Data
@NoArgsConstructor
public class GetUserNicknameDTO implements Serializable {

    /**
     * The nickname of the user.
     */
    private String nickname;

    /**
     * Constructor for the GetUserNicknameDTO class.
     * @param nickname the nickname of the user
     */
    public GetUserNicknameDTO(String nickname) {
        this.nickname = nickname;
    }
}