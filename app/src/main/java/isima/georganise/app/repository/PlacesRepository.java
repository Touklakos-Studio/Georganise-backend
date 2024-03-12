package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlacesRepository extends JpaRepository<Place, Long> {
    Optional<List<Place>> findByUserId(Long id);

    Optional<List<Place>> findByTagId(Long id);

    Optional<List<Place>> findByKeyword(String keyword);

    Optional<List<Place>> findByVicinity();
}
