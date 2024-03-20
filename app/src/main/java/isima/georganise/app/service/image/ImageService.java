package isima.georganise.app.service.image;

import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * This interface represents the ImageService.
 * It provides methods to interact with the Image entity.
 */
@Service
public interface ImageService {

    /**
     * This method retrieves all images for a given user.
     * It returns an Iterable of ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @return An Iterable of ImageDTO.
     */
    Iterable<ImageDTO> getAllImages(UUID authToken);

    /**
     * This method retrieves an image by its id for a given user.
     * It returns an ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the image.
     * @return An ImageDTO.
     */
    ImageDTO getImageById(UUID authToken, Long id);

    /**
     * This method retrieves images by keyword for a given user.
     * It returns an Iterable of ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @param keyword The keyword to search for in the images.
     * @return An Iterable of ImageDTO.
     */
    Iterable<ImageDTO> getImageByKeyword(UUID authToken, String keyword);

    /**
     * This method creates an image for a given user.
     * It returns an ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @param image The ImageCreationDTO containing the data to create the image.
     * @return An ImageDTO.
     */
    ImageDTO createImage(UUID authToken, ImageCreationDTO image);

    /**
     * This method deletes an image by its id for a given user.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the image.
     */
    void deleteImage(UUID authToken, Long id);

    /**
     * This method updates an image by its id for a given user.
     * It returns an ImageDTO.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the image.
     * @param imageUpdateDTO The ImageUpdateDTO containing the data to update the image.
     * @return An ImageDTO.
     */
    ImageDTO updateImage(UUID authToken, Long id, ImageUpdateDTO imageUpdateDTO);

}
