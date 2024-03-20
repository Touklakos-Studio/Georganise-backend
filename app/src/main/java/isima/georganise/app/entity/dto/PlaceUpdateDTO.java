package isima.georganise.app.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class represents the PlaceUpdateDTO data transfer object with all its properties.
 * It is used to transfer data for updating a place.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class PlaceUpdateDTO {

    /**
     * The new latitude of the place.
     */
    private BigDecimal latitude;

    /**
     * The new longitude of the place.
     */
    private BigDecimal longitude;

    /**
     * The new name of the place.
     */
    private String name;

    /**
     * The new description of the place.
     */
    private String description;

    /**
     * The ID of the new image associated with the place.
     */
    private Long imageId;

    /**
     * The list of new tag IDs associated with the place.
     */
    private List<Long> tagIds;
}