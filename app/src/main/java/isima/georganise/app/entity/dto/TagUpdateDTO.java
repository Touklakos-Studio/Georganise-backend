package isima.georganise.app.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TagUpdateDTO implements Serializable {

    private String title;

    private String description;

    private List<Long> placeIds;
}
