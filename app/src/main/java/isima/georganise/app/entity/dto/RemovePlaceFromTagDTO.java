package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * This class represents the RemovePlaceFromTagDTO data transfer object with all its properties.
 * It is used to transfer data for removing a place from a tag.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class RemovePlaceFromTagDTO implements Serializable {

    /**
     * The ID of the place to be removed from the tag.
     */
    private Long placeId;
}