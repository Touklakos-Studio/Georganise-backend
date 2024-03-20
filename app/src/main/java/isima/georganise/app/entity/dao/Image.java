package isima.georganise.app.entity.dao;

import isima.georganise.app.entity.dto.ImageCreationDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * This class represents the Image entity with all its properties.
 * It uses the @Entity annotation to indicate that it is a JPA entity.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "IMAGES")
public class Image implements Serializable {

    /**
     * The unique ID of the image. It is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long imageId;

    /**
     * The ID of the user who uploaded the image.
     */
    @Column(name = "USERID", updatable = false, nullable = false)
    private Long userId;

    /**
     * The actual image data stored as a base 64 byte array.
     */
    @Column(name = "IMAGE", updatable = false, nullable = false)
    private byte[] imageValue;

    /**
     * The name of the image.
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * The description of the image.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * A boolean value indicating whether the image is public or not.
     */
    @Column(name = "PUBLIC", nullable = false)
    private boolean isPublic;

    /**
     * Constructor for the Image class.
     * @param imageCreationDTO the image creation DTO
     * @param userId the user ID
     */
    public Image(@NotNull ImageCreationDTO imageCreationDTO, Long userId) {
        this.userId = userId;
        this.imageValue = imageCreationDTO.getImageValue().getBytes();
        this.name = imageCreationDTO.getName();
        this.description = imageCreationDTO.getDescription();
        this.isPublic = imageCreationDTO.getIsPublic();
    }
}