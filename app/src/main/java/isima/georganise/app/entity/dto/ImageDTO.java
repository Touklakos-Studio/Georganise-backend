package isima.georganise.app.entity.dto;

import isima.georganise.app.entity.dao.Image;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * This class represents the ImageDTO data transfer object with all its properties.
 * It is used to transfer data for an image.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 */
@Data
@NoArgsConstructor
public class ImageDTO implements Serializable {

    /**
     * The unique ID of the image.
     */
    private Long imageId;

    /**
     * The ID of the user who uploaded the image.
     */
    private Long userId;

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
    private boolean isPublic;

    /**
     * Constructor for the ImageDTO class.
     * @param image the image DAO
     */
    public ImageDTO(@NotNull Image image) {
        this.imageId = image.getImageId();
        this.userId = image.getUserId();
        this.imageValue = Base64.getEncoder().encodeToString(Base64.getDecoder().decode(image.getImageValue()));
        this.name = image.getName();
        this.description = image.getDescription();
        this.isPublic = image.isPublic();
    }

    /**
     * This method converts a list of Image DAOs to a list of ImageDTOs.
     * @param images the iterable of Image DAOs
     * @return a list of ImageDTOs
     */
    public static @NotNull List<ImageDTO> fromImages(@NotNull Iterable<Image> images) {
        List<ImageDTO> imageDTOS = new ArrayList<>();
        for (Image image : images) {
            imageDTOS.add(new ImageDTO(image));
        }
        return imageDTOS;
    }
}