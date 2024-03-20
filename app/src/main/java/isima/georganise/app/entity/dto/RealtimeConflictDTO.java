package isima.georganise.app.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * This class represents the RealtimeConflictDTO data transfer object with all its properties.
 * It is used to transfer data for a realtime conflict.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 */
@Data
@NoArgsConstructor
public class RealtimeConflictDTO implements Serializable {

    /**
     * The ID of the place where the conflict occurred.
     */
    private long placeId;

    /**
     * Constructor for the RealtimeConflictDTO class.
     * @param placeId the ID of the place where the conflict occurred
     */
    public RealtimeConflictDTO(long placeId) {
        this.placeId = placeId;
    }
}