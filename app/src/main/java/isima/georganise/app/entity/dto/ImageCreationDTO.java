package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * This class represents the ImageCreationDTO data transfer object with all its properties.
 * It is used to transfer data for creating an image.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class ImageCreationDTO implements Serializable {

    /**
     * The value of the image. It is a string representation of the image.
     */
    private String imageValue;

    /**
     * The name of the image.
     */
    private String name;

    /**
     * The description of the image.
     */
    private String description;

    /**
     * A boolean value indicating whether the image is public or not.
     */
    private Boolean isPublic;
}