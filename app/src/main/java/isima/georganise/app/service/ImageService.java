package isima.georganise.app.service;

import isima.georganise.app.entity.dao.Image;

import java.util.List;

public interface ImageService {
    public List<Image> images();
    public Image image(Long id);
    public Image addImage(Image image);
    public boolean deleteImage(Long id);
    public Image updateImage(Long id,Image image);
}
