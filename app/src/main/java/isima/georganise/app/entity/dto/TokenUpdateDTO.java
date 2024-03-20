package isima.georganise.app.entity.dto;

import isima.georganise.app.entity.util.Right;
import lombok.Data;

import java.io.Serializable;

/**
 * This class represents the TokenUpdateDTO data transfer object with all its properties.
 * It is used to transfer data for updating a token.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class TokenUpdateDTO implements Serializable {

    /**
     * The new access right of the token.
     */
    private Right accessRight;
}