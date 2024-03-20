package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class represents the GetPlaceVicinityDTO data transfer object with all its properties.
 * It is used to transfer data for getting places in a certain vicinity.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 */
@Data
public class GetPlaceVicinityDTO implements Serializable {

    /**
     * The latitude of the center point for the vicinity search.
     */
    private BigDecimal latitude;

    /**
     * The longitude of the center point for the vicinity search.
     */
    private BigDecimal longitude;

    /**
     * The radius around the center point for the vicinity search.
     */
    private BigDecimal radius;
}