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

    @Query("SELECT p FROM Place p JOIN p.placeTags t WHERE t.tag.tagId = :id")
    Optional<List<Place>> findByTagId(Long id);

    @Query("SELECT p FROM Place p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    Optional<List<Place>> findByKeyword(String keyword);

    @Query("SELECT p FROM Place p WHERE p.longitude BETWEEN :minLongitude AND :maxLongitude AND p.latitude BETWEEN :minLatitude AND :maxLatitude")
    Optional<List<Place>> findByVicinity(BigDecimal minLongitude, BigDecimal maxLongitude, BigDecimal minLatitude, BigDecimal maxLatitude);
}
