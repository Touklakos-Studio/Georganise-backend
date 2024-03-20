package isima.georganise.app.service.image;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.exception.NotLoggedException;
import isima.georganise.app.exception.UnauthorizedException;
import isima.georganise.app.repository.ImagesRepository;
import isima.georganise.app.repository.PlacesRepository;
import isima.georganise.app.repository.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.UUID;

/**
 * This class implements the ImageService interface.
 * It provides methods to interact with the Image entity.
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final @NotNull ImagesRepository imagesRepository;
    private final @NotNull UsersRepository usersRepository;
    private final @NotNull PlacesRepository placesRepository;

    /**
     * This constructor initializes the ImageServiceImpl with the necessary repositories.
     *
     * @param imagesRepository The repository to interact with the Image entity.
     * @param usersRepository The repository to interact with the User entity.
     * @param placesRepository The repository to interact with the Place entity.
     */
    @Autowired
    public ImageServiceImpl(@NotNull ImagesRepository imagesRepository, @NotNull UsersRepository usersRepository, @NotNull PlacesRepository placesRepository) {
        Assert.notNull(imagesRepository, "ImagesRepository cannot be null");
        Assert.notNull(usersRepository, "UsersRepository cannot be null");
        Assert.notNull(placesRepository, "PlaceService cannot be null");
        this.imagesRepository = imagesRepository;
        this.usersRepository = usersRepository;
        this.placesRepository = placesRepository;
    }

    /**
     * This method retrieves all images for a given user.
     * It returns an Iterable of ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @return An Iterable of ImageDTO.
     */
    @Override
    public @NotNull Iterable<ImageDTO> getAllImages(UUID authToken) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Iterable<Image> images = imagesRepository.findAllPublic(currentUser.getUserId());

        return ImageDTO.fromImages(images);
    }

    /**
     * This method retrieves an image by its id for a given user.
     * It returns an ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the image.
     * @return An ImageDTO.
     */
    @Override
    public @NotNull ImageDTO getImageById(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Image image = imagesRepository.findByImageIdAndUserId(id, userCurrent.getUserId()).orElseThrow(NotFoundException::new);

        return new ImageDTO(image);
    }

    /**
     * This method retrieves images by keyword for a given user.
     * It returns an Iterable of ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @param keyword The keyword to search for in the images.
     * @return An Iterable of ImageDTO.
     */
    @Override
    public @NotNull Iterable<ImageDTO> getImageByKeyword(UUID authToken, String keyword) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Iterable<Image> images = imagesRepository.findByKeywordAndUserId(keyword, userCurrent.getUserId()).orElseThrow(NotFoundException::new);

        return ImageDTO.fromImages(images);
    }

    /**
     * This method creates an image for a given user.
     * It returns an ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @param imageCreation The ImageCreationDTO containing the data to create the image.
     * @return An ImageDTO.
     */
    @Override
    public @NotNull ImageDTO createImage(UUID authToken, @NotNull ImageCreationDTO imageCreation) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (Objects.isNull(imageCreation.getName())) throw new IllegalArgumentException("Name cannot be null");
        if (Objects.isNull(imageCreation.getImageValue())) throw new IllegalArgumentException("Image value cannot be null");
        if (Objects.isNull(imageCreation.getIsPublic())) throw new IllegalArgumentException("Is public cannot be null");

        return new ImageDTO(imagesRepository.saveAndFlush(new Image(imageCreation, userCurrent.getUserId())));
    }

    /**
     * This method deletes an image by its id for a given user.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the image.
     */
    @Override
    public void deleteImage(UUID authToken, Long id) {
        Image image = checkImageAccessRights(authToken, id);

        placesRepository.findByImageId(id).forEach(place -> {
            place.setImageId(null);
            placesRepository.saveAndFlush(place);
        });

        imagesRepository.delete(image);
    }

    /**
     * This method updates an image by its id for a given user.
     * It returns an ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the image.
     * @param imageUpdateDTO The ImageUpdateDTO containing the data to update the image.
     * @return An ImageDTO.
     */
    @Override
    public @NotNull ImageDTO updateImage(UUID authToken, Long id, @NotNull ImageUpdateDTO imageUpdateDTO) {
        Image image = checkImageAccessRights(authToken, id);

        if (imageUpdateDTO.getName() != null) image.setName(imageUpdateDTO.getName());
        if (imageUpdateDTO.getDescription() != null) image.setDescription(imageUpdateDTO.getDescription());
        if (imageUpdateDTO.getIsPublic() != null) image.setPublic(imageUpdateDTO.getIsPublic());

        return new ImageDTO(imagesRepository.saveAndFlush(image));
    }

    /**
     * This method checks if the user has access rights to the image.
     * It returns an Image.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the image.
     * @return An Image.
     */
    @NotNull
    private Image checkImageAccessRights(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Image image = imagesRepository.findByImageIdAndUserId(id, userCurrent.getUserId()).orElseThrow(NotFoundException::new);

        if (!image.getUserId().equals(userCurrent.getUserId())) {
            throw new UnauthorizedException(userCurrent.getNickname(), "update image " + id);
        }

        return image;
    }
}