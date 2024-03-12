package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long>{
    Optional<Tag> findByKeyword(String keyword);
}
