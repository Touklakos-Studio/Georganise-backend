package isima.georganise.app.service.image;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ImageService {

    Iterable<Image> getAllImages(UUID authToken);

    Image getImageById(UUID authToken, Long id);

    Iterable<Image> getImageByKeyword(UUID authToken, String keyword);

    Image createImage(UUID authToken, ImageCreationDTO image);

    void deleteImage(UUID authToken, Long id);

    Image updateImage(UUID authToken, Long id, ImageUpdateDTO imageUpdateDTO);

}
