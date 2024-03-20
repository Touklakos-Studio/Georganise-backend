package isima.georganise.app.entity.dto;

import isima.georganise.app.entity.util.Right;
import lombok.Data;

import java.io.Serializable;

/**
 * This class represents the TokenCreationDTO data transfer object with all its properties.
 * It is used to transfer data for creating a token.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class TokenCreationDTO implements Serializable {

    /**
     * The access right of the token.
     */
    private Right accessRight;

    /**
     * The nickname associated with the token.
     */
    private String nickname;

    /**
     * The ID of the tag associated with the token.
     */
    private Long tagId;
}