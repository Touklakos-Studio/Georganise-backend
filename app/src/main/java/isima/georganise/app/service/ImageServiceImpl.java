package isima.georganise.app.service;

import isima.georganise.app.entity.dao.Image;
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
    public List<Image> images() {
        return imagesRepository.findAll();
    }

    @Override
    public Image getImagebyId(Long id) {
        Optional<Image> op = imagesRepository.findById(id);
        return op.orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Image> getImageByKeyword(String keyword) {
        Optional<List<Image>> op = imagesRepository.findByKeyword(keyword);
        if (op.isEmpty()) throw new NotFoundException();
        return op.get();
    }

    @Override
    public Image addImage(Image image) {
        return imagesRepository.save(image);
    }

    @Override
    public void deleteImage(Long id) {
        Optional<Image> u = imagesRepository.findById(id);
        if(u.isEmpty()) throw new NotFoundException();

        imagesRepository.deleteById(id);
    }

    @Override
    public Image updateImage(Long id, Image image) {
        Optional<Image> optionalImage = imagesRepository.findById(id);

        if (optionalImage.isPresent()) {
            Image existingImage = optionalImage.get();
            if(image.getImage() != null)
                existingImage.setImage(image.getImage());
            if(image.getName() != null)
                existingImage.setName(image.getName());
            if(image.getDescription() != null)
                existingImage.setDescription(image.getDescription());


            return imagesRepository.save(existingImage);
        } else {
            throw new NotFoundException();
        }
    }
}

