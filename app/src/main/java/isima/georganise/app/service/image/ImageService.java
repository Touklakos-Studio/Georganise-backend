package isima.georganise.app.service.image;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ImageService {

    public Iterable<Image> getAllImages(UUID authToken);

    public Image getImageById(UUID authToken, Long id);

    public Iterable<Image> getImageByKeyword(UUID authToken, String keyword);

    public Image createImage(UUID authToken, ImageCreationDTO image);

    public void deleteImage(UUID authToken, Long id);

    public Image updateImage(UUID authToken, Long id, ImageUpdateDTO imageUpdateDTO);

}
