package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long>{

    @Query("SELECT t FROM Tag t WHERE t.title LIKE :keyword% OR t.description LIKE :keyword%")
    Optional<Iterable<Tag>> findByKeyword(String keyword);
}
