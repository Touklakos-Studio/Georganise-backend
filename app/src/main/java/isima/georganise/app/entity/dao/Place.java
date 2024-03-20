package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * This class represents the Place entity with all its properties.
 * It uses the @Entity annotation to indicate that it is a JPA entity.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "PLACES")
public class Place implements Serializable {

    /**
     * The unique ID of the place. It is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long placeId;

    /**
     * The latitude of the place.
     */
    @Column(name = "LATITUDE", nullable = false)
    private BigDecimal latitude;

    /**
     * The longitude of the place.
     */
    @Column(name = "LONGITUDE", nullable = false)
    private BigDecimal longitude;

    /**
     * The name of the place.
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * The description of the place.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * The ID of the image associated with the place.
     */
    @Column(name = "IMAGEID")
    private Long imageId;

    /**
     * The ID of the user who created the place.
     */
    @Column(name = "USERID", updatable = false, nullable = false)
    private Long userId;

    /**
     * The list of tags associated with the place.
     */
    @OneToMany(mappedBy = "place")
    @JsonManagedReference
    private List<PlaceTag> placeTags;

    /**
     * Constructor for the Place class.
     * @param placeCreationDTO the place creation DTO
     * @param userId the user ID
     */
    public Place(@NotNull PlaceCreationDTO placeCreationDTO, Long userId) {
        this.latitude = placeCreationDTO.getLatitude();
        this.longitude = placeCreationDTO.getLongitude();
        this.name = placeCreationDTO.getName();
        this.description = placeCreationDTO.getDescription();
        this.imageId = placeCreationDTO.getImageId();
        this.userId = userId;
    }
}