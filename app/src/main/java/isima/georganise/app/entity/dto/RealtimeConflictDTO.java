package isima.georganise.app.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RealtimeConflictDTO implements Serializable {

    public long placeId;

    public RealtimeConflictDTO(long placeId) {
        this.placeId = placeId;
    }
}
