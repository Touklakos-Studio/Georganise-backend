package isima.georganise.app.service;

import isima.georganise.app.entity.dao.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    public List<Image> images();
    public Image getImagebyId(Long id);
    public List<Image> getImageByKeyword(String keyword);
    public Image addImage(Image image);
    public void deleteImage(Long id);
    public Image updateImage(Long id,Image image);
}
