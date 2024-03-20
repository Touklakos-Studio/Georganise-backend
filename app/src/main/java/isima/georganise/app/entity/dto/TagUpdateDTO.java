package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * This class represents the TagUpdateDTO data transfer object with all its properties.
 * It is used to transfer data for updating a tag.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class TagUpdateDTO implements Serializable {

    /**
     * The new description of the tag.
     */
    private String description;
}