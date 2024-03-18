package isima.georganise.app.service.image;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.exception.NotLoggedException;
import isima.georganise.app.repository.ImagesRepository;
import isima.georganise.app.repository.PlacesRepository;
import isima.georganise.app.repository.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final @NotNull ImagesRepository imagesRepository;

    private final @NotNull UsersRepository usersRepository;

    private final @NotNull PlacesRepository placesRepository;

    @Autowired
    public ImageServiceImpl(@NotNull ImagesRepository imagesRepository, @NotNull UsersRepository usersRepository, @NotNull PlacesRepository placesRepository) {
        Assert.notNull(imagesRepository, "ImagesRepository cannot be null");
        Assert.notNull(usersRepository, "UsersRepository cannot be null");
        Assert.notNull(placesRepository, "PlaceService cannot be null");
        this.imagesRepository = imagesRepository;
        this.usersRepository = usersRepository;
        this.placesRepository = placesRepository;
    }

    @Override
    public Iterable<ImageDTO> getAllImages(UUID authToken) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());
        Iterable<Image> images = imagesRepository.findAllPublic(currentUser.getUserId());
        System.out.println("\tfetched " + images.spliterator().getExactSizeIfKnown() + " images");

        return ImageDTO.fromImages(images);
    }

    @Override
    public ImageDTO getImageById(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        Image image = imagesRepository.findByImageIdAndUserId(id, userCurrent.getUserId()).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched image: " + image.getImageId());

        return new ImageDTO(image);
    }

    @Override
    public Iterable<ImageDTO> getImageByKeyword(UUID authToken, String keyword) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        Iterable<Image> images = imagesRepository.findByKeywordAndUserId(keyword, userCurrent.getUserId()).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched " + images.spliterator().getExactSizeIfKnown() + " images");

        return ImageDTO.fromImages(images);
    }

    @Override
    public @NotNull ImageDTO createImage(UUID authToken, @NotNull ImageCreationDTO imageCreation) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());

        if (Objects.isNull(imageCreation.getName())) throw new IllegalArgumentException("Name cannot be null");
        if (Objects.isNull(imageCreation.getImageValue())) throw new IllegalArgumentException("Image value cannot be null");
        if (Objects.isNull(imageCreation.getIsPublic())) throw new IllegalArgumentException("Is public cannot be null");

        Image image;
        try {
            image = imagesRepository.saveAndFlush(new Image(imageCreation, userCurrent.getUserId()));
            System.out.println("\tcreated image: " + image.getImageId());
        } catch (Exception e) {
            System.out.println("\tfailed to create image");
            throw new IllegalArgumentException(e.getMessage());
        }

        return new ImageDTO(image);
    }

    @Override
    public void deleteImage(UUID authToken, Long id) {
        Image image = checkImageAccessRights(authToken, id);

        List<Place> places = placesRepository.findByImageId(id);
        System.out.println("\tfetched " + places.size() + " places");
        for (Place place : places) {
            place.setImageId(null);
            placesRepository.saveAndFlush(place);
            System.out.println("\t\tupdated place: " + place.getPlaceId());
        }

        imagesRepository.delete(image);
        System.out.println("\tdeleted image: " + image.getImageId());
    }

    @Override
    public @NotNull ImageDTO updateImage(UUID authToken, Long id, @NotNull ImageUpdateDTO imageUpdateDTO) {
        Image image = checkImageAccessRights(authToken, id);

        if (imageUpdateDTO.getName() != null) {
            System.out.println("\tupdating name to: " + imageUpdateDTO.getName() + " from: " + image.getName());
            image.setName(imageUpdateDTO.getName());
        }
        if (imageUpdateDTO.getDescription() != null) {
            System.out.println("\tupdating description to: " + imageUpdateDTO.getDescription() + " from: " + image.getDescription());
            image.setDescription(imageUpdateDTO.getDescription());
        }
        if (imageUpdateDTO.getIsPublic() != null) {
            System.out.println("\tupdating isPublic to: " + imageUpdateDTO.getIsPublic() + " from: " + image.isPublic());
            image.setPublic(imageUpdateDTO.getIsPublic());
        }

        Image imageUpdated = imagesRepository.saveAndFlush(image);
        System.out.println("\tupdated image: " + imageUpdated.getImageId());
        return new ImageDTO(imageUpdated);
    }

    @NotNull
    private Image checkImageAccessRights(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        Image image = imagesRepository.findByImageIdAndUserId(id, userCurrent.getUserId()).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched image: " + image.getImageId());

        if (!image.getUserId().equals(userCurrent.getUserId())) {
            System.out.println("\timage does not belong to user: " + userCurrent.getUserId() + " but to: " + image.getUserId());
            throw new NotFoundException();
        }

        System.out.println("\timage belongs to user: " + userCurrent.getUserId());
        return image;
    }
}

