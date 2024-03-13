package isima.georganise.app.service.image;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {

    public List<Image> getAllImages();

    public Image getImageById(Long id);

    public List<Image> getImageByKeyword(String keyword);

    public Image createImage(ImageCreationDTO image);

    public void deleteImage(Long id);

    public Image updateImage(Long id, ImageUpdateDTO imageUpdateDTO);

}
