package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * This class represents the PlaceCreationDTO data transfer object with all its properties.
 * It is used to transfer data for creating a place.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class PlaceCreationDTO implements Serializable {

    /**
     * The latitude of the place.
     */
    private BigDecimal latitude;

    /**
     * The longitude of the place.
     */
    private BigDecimal longitude;

    /**
     * The name of the place.
     */
    private String name;

    /**
     * The description of the place.
     */
    private String description;

    /**
     * The list of tag IDs associated with the place.
     */
    private List<Long> tagIds;

    /**
     * The ID of the image associated with the place.
     */
    private Long imageId;

    /**
     * A boolean value indicating whether the place is realtime or not.
     */
    private boolean realtime;
}