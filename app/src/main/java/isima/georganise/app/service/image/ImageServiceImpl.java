package isima.georganise.app.service.image;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    ImagesRepository imagesRepository;

    @Override
    public List<Image> getAllImages() {
        return imagesRepository.findAll();
    }

    @Override
    public Image getImageById(Long id) {
        return imagesRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Image> getImageByKeyword(String keyword) {
        return imagesRepository.findByKeyword(keyword).orElseThrow(NotFoundException::new);
    }

    @Override
    public Image createImage(ImageCreationDTO image) {
        return imagesRepository.saveAndFlush(new Image(image));
    }

    @Override
    public void deleteImage(Long id) {
        imagesRepository.delete(imagesRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Image updateImage(Long id, ImageUpdateDTO imageUpdateDTO) {
        Image image = imagesRepository.findById(id).orElseThrow(NotFoundException::new);

        if(imageUpdateDTO.getName() != null)
            image.setName(imageUpdateDTO.getName());
        if(imageUpdateDTO.getDescription() != null)
            image.setDescription(imageUpdateDTO.getDescription());

        return imagesRepository.saveAndFlush(image);
    }
}

