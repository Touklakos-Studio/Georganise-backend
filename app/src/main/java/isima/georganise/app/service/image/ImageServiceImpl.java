package isima.georganise.app.service.image;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.exception.NotLoggedException;
import isima.georganise.app.repository.ImagesRepository;
import isima.georganise.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Iterable<Image> getAllImages(UUID authToken) {
        usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return imagesRepository.findAllPublic();
    }

    @Override
    public Image getImageById(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return imagesRepository.findByImageIdAndUserId(id, userCurrent.getUserId()).orElseThrow(NotFoundException::new);
    }

    @Override
    public Iterable<Image> getImageByKeyword(UUID authToken, String keyword) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return imagesRepository.findByKeywordAndUserId(keyword, userCurrent.getUserId()).orElseThrow(NotFoundException::new);
    }

    @Override
    public Image createImage(UUID authToken, ImageCreationDTO imageCreation) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        Image image;
        try {
            image = imagesRepository.saveAndFlush(new Image(imageCreation, userCurrent.getUserId()));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return image;
    }

    @Override
    public void deleteImage(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Image image = imagesRepository.findByImageIdAndUserId(id, userCurrent.getUserId()).orElseThrow(NotFoundException::new);

        if (!image.getUserId().equals(userCurrent.getUserId()))
            throw new NotFoundException();

        imagesRepository.delete(image);
    }

    @Override
    public Image updateImage(UUID authToken, Long id, ImageUpdateDTO imageUpdateDTO) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Image image = imagesRepository.findByImageIdAndUserId(id, userCurrent.getUserId()).orElseThrow(NotFoundException::new);

        if (!image.getUserId().equals(userCurrent.getUserId()))
            throw new NotFoundException();

        if(imageUpdateDTO.getName() != null)
            image.setName(imageUpdateDTO.getName());
        if(imageUpdateDTO.getDescription() != null)
            image.setDescription(imageUpdateDTO.getDescription());
        if(imageUpdateDTO.getIsPublic() != null)
            image.setPublic(imageUpdateDTO.getIsPublic());

        return imagesRepository.saveAndFlush(image);
    }
}

