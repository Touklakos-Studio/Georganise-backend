package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Image, Long> {
    Optional<List<Image>> findByKeyword(String keyword);
}
