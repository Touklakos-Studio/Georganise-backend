package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long>{

    @Query("SELECT ta FROM Tag ta JOIN Token to WHERE (ta.title LIKE %:keyword% OR ta.description LIKE %:keyword%) AND (ta.userId = :userId OR to.userId = :userId)")
    Optional<Iterable<Tag>> findByKeywordAndUserId(String keyword, Long userId);
}
