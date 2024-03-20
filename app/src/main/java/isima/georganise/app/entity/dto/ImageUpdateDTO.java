package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * This class represents the ImageUpdateDTO data transfer object with all its properties.
 * It is used to transfer data for updating an image.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class ImageUpdateDTO implements Serializable {

    /**
     * The new name of the image.
     */
    private String name;

    /**
     * The new description of the image.
     */
    private String description;

    /**
     * A boolean value indicating whether the image is public or not.
     */
    private Boolean isPublic;
}