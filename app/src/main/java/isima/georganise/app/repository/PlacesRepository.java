package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlacesRepository extends JpaRepository<Place, Long> {

    Optional<List<Place>> findByUserId(Long id);

    @Query("SELECT p FROM Place p JOIN PlaceTag pt WHERE pt.tag.tagId = :tagId AND p.userId = :userId")
    Optional<List<Place>> findByTagIdAndUserId(Long tagId, Long userId);

    @Query("SELECT p FROM Place p WHERE (p.name LIKE %:keyword% OR p.description LIKE %:keyword%) AND p.userId = :userId")
    Optional<List<Place>> findByKeywordAndUserId(String keyword, Long userId);

    @Query("SELECT p FROM Place p WHERE p.longitude BETWEEN :minLongitude AND :maxLongitude AND p.latitude BETWEEN :minLatitude AND :maxLatitude AND p.userId = :userId")
    Optional<List<Place>> findByVicinityAndUserId(BigDecimal minLongitude, BigDecimal maxLongitude, BigDecimal minLatitude, BigDecimal maxLatitude, Long userId);

}
