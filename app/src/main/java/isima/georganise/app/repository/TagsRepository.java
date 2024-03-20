package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents the TagsRepository.
 * It extends JpaRepository and provides methods to interact with the Tag entity in the database.
 */
@Repository
public interface TagsRepository extends JpaRepository<Tag, Long> {

    /**
     * This method finds tags by keyword.
     * It returns a List of Tag.
     *
     * @param keyword The keyword to search for in the title and description of the tags.
     * @return A List of Tag.
     */
    @Query("SELECT ta FROM Tag ta WHERE (UPPER(ta.title) LIKE UPPER(CONCAT('%', :keyword, '%')) OR UPPER(ta.description) LIKE UPPER(CONCAT('%', :keyword, '%')))")
    List<Tag> findByKeyword(String keyword);

    /**
     * This method finds a tag by title.
     * It returns an Optional of Tag.
     *
     * @param title The title of the tag.
     * @return An Optional of Tag.
     */
    Optional<Tag> findByTitle(String title);

    /**
     * This method finds tags by user id.
     * It returns a List of Tag.
     *
     * @param userId The id of the user.
     * @return A List of Tag.
     */
    List<Tag> findByUserId(Long userId);

    /**
     * This method finds a tag by user id and title.
     * It returns an Optional of Tag.
     *
     * @param userId The id of the user.
     * @param title The title of the tag.
     * @return An Optional of Tag.
     */
    Optional<Tag> findByUserIdAndTitle(Long userId, String title);
}