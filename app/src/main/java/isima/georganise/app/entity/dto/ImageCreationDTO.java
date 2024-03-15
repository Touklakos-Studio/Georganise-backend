package isima.georganise.app.entity.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageCreationDTO {

    private MultipartFile imageValue;

    private String name;

    private String description;

    private boolean isPublic;
}
