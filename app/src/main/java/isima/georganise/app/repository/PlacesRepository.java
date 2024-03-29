package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * This interface represents the PlacesRepository.
 * It extends JpaRepository and provides methods to interact with the Place entity in the database.
 */
@Repository
public interface PlacesRepository extends JpaRepository<Place, Long> {

    /**
     * This method finds places by user id.
     * It returns a List of Place.
     *
     * @param id The id of the user.
     * @return A List of Place.
     */
    List<Place> findByUserId(Long id);

    /**
     * This method finds places by tag id.
     * It returns a List of Place.
     *
     * @param id The id of the tag.
     * @return A List of Place.
     */
    @Query("SELECT p FROM Place p JOIN PlaceTag pt ON pt.place.placeId = p.placeId WHERE pt.tag.tagId = :id")
    List<Place> findByTagId(Long id);

    /**
     * This method finds places by keyword.
     * It returns a List of Place.
     *
     * @param keyword The keyword to search for in the name and description of the places and tags.
     * @return A List of Place.
     */
    @Query("SELECT p FROM Place p LEFT JOIN PlaceTag pt ON pt.place.placeId = p.placeId LEFT JOIN Tag t ON pt.tag.tagId = t.tagId WHERE UPPER(p.name) LIKE UPPER(CONCAT('%', :keyword, '%')) OR UPPER(p.description) LIKE UPPER(CONCAT('%', :keyword, '%')) OR UPPER(pt.tag.title) LIKE UPPER(CONCAT('%', :keyword, '%')) OR UPPER(pt.tag.description) LIKE UPPER(CONCAT('%', :keyword, '%'))")
    List<Place> findByKeyword(String keyword);

    /**
     * This method finds places by vicinity and user id.
     * It returns an Optional of List of Place.
     *
     * @param minLongitude The minimum longitude of the vicinity.
     * @param maxLongitude The maximum longitude of the vicinity.
     * @param minLatitude The minimum latitude of the vicinity.
     * @param maxLatitude The maximum latitude of the vicinity.
     * @param userId The id of the user.
     * @return An Optional of List of Place.
     */
    @Query("SELECT p FROM Place p JOIN PlaceTag pt ON pt.place.placeId = p.placeId JOIN Token t ON pt.tag.tagId = t.tagId WHERE p.latitude BETWEEN :minLatitude AND :maxLatitude AND p.longitude BETWEEN :minLongitude AND :maxLongitude AND (t.userId = :userId OR p.userId = :userId)")
    Optional<List<Place>> findByVicinityAndUserId(BigDecimal minLongitude, BigDecimal maxLongitude, BigDecimal minLatitude, BigDecimal maxLatitude, Long userId);

    /**
     * This method finds places by image id.
     * It returns a List of Place.
     *
     * @param imageId The id of the image.
     * @return A List of Place.
     */
    List<Place> findByImageId(Long imageId);

}