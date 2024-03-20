package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * This class represents the TagCreationDTO data transfer object with all its properties.
 * It is used to transfer data for creating a tag.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class TagCreationDTO implements Serializable {

    /**
     * The title of the tag.
     */
    private String title;

    /**
     * The description of the tag.
     */
    private String description;
}