package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This interface represents the TokensRepository.
 * It extends JpaRepository and provides methods to interact with the Token entity in the database.
 */
@Repository
public interface TokensRepository extends JpaRepository<Token, Long> {

    /**
     * This method finds Token entities by user id.
     * It returns an Optional of Iterable of Token.
     *
     * @param id The id of the user.
     * @return An Optional of Iterable of Token.
     */
    Optional<Iterable<Token>> findByUserId(Long id);

    /**
     * This method finds Token entities by creator id.
     * It returns an Optional of Iterable of Token.
     *
     * @param id The id of the creator.
     * @return An Optional of Iterable of Token.
     */
    Optional<Iterable<Token>> findByCreatorId(Long id);

    /**
     * This method finds Token entities by user id and tag id.
     * It returns a List of Token.
     *
     * @param userId The id of the user.
     * @param tagId The id of the tag.
     * @return A List of Token.
     */
    List<Token> findByUserIdAndTagId(Long userId, Long tagId);

    /**
     * This method finds Token entities by tag id.
     * It returns a List of Token.
     *
     * @param tagId The id of the tag.
     * @return A List of Token.
     */
    List<Token> findByTagId(Long tagId);

    /**
     * This method finds a Token entity by token value.
     * It returns an Optional of Token.
     *
     * @param token The value of the token.
     * @return An Optional of Token.
     */
    Optional<Token> findByTokenValue(UUID token);

    /**
     * This method finds Token entities by creator id and tag id.
     * It returns a List of Token.
     *
     * @param userId The id of the creator.
     * @param id The id of the tag.
     * @return A List of Token.
     */
    List<Token> findByCreatorIdAndTagId(Long userId, Long id);
}