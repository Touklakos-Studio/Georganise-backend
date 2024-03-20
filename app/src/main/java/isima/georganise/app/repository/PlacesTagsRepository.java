package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.PlaceTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface represents the PlacesTagsRepository.
 * It extends JpaRepository and provides methods to interact with the PlaceTag entity in the database.
 */
@Repository
public interface PlacesTagsRepository extends JpaRepository<PlaceTag, Long> {

    /**
     * This method finds PlaceTag entities by tag id.
     * It returns an Iterable of PlaceTag.
     *
     * @param id The id of the tag.
     * @return An Iterable of PlaceTag.
     */
    Iterable<PlaceTag> findByTag_TagId(Long id);

    /**
     * This method finds a PlaceTag entity by tag id and place id.
     * It returns an Optional of PlaceTag.
     *
     * @param id The id of the tag.
     * @param placeId The id of the place.
     * @return An Optional of PlaceTag.
     */
    Optional<PlaceTag> findByTag_TagIdAndPlace_PlaceId(Long id, Long placeId);

    /**
     * This method finds PlaceTag entities by place id.
     * It returns an Iterable of PlaceTag.
     *
     * @param placeId The id of the place.
     * @return An Iterable of PlaceTag.
     */
    Iterable<PlaceTag> findByPlace_PlaceId(Long placeId);
}