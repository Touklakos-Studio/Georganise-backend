package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents the ImagesRepository.
 * It extends JpaRepository and provides methods to interact with the Image entity in the database.
 */
@Repository
public interface ImagesRepository extends JpaRepository<Image, Long> {

    /**
     * This method finds images by keyword and user id.
     * It returns an Optional of Iterable of Image.
     *
     * @param keyword The keyword to search for in the name and description of the images.
     * @param userId The id of the user.
     * @return An Optional of Iterable of Image.
     */
    @Query("SELECT i FROM Image i WHERE (i.name LIKE %:keyword% OR i.description LIKE %:keyword%) AND (i.userId = :userId OR i.isPublic)")
    Optional<Iterable<Image>> findByKeywordAndUserId(String keyword, Long userId);

    /**
     * This method finds an image by image id and user id.
     * It returns an Optional of Image.
     *
     * @param imageId The id of the image.
     * @param userId The id of the user.
     * @return An Optional of Image.
     */
    @Query("SELECT i FROM Image i WHERE i.imageId = :imageId AND (i.userId = :userId OR i.isPublic)")
    Optional<Image> findByImageIdAndUserId(Long imageId, Long userId);

    /**
     * This method finds all public images and images of a specific user.
     * It returns an Iterable of Image.
     *
     * @param userId The id of the user.
     * @return An Iterable of Image.
     */
    @Query("SELECT i FROM Image i WHERE i.isPublic OR i.userId = :userId")
    Iterable<Image> findAllPublic(long userId);

    /**
     * This method finds images by user id.
     * It returns a List of Image.
     *
     * @param userId The id of the user.
     * @return A List of Image.
     */
    List<Image> findByUserId(Long userId);
}