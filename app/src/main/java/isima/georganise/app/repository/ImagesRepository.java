package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i WHERE i.name LIKE %:keyword% OR i.description LIKE %:keyword%")
    Optional<List<Image>> findByKeyword(String keyword);
}
