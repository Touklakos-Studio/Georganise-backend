package isima.georganise.app.repository;

import isima.georganise.app.entity.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * This interface represents the UsersRepository.
 * It extends JpaRepository and provides methods to interact with the User entity in the database.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    /**
     * This method finds a user by email.
     * It returns an Optional of User.
     *
     * @param email The email of the user.
     * @return An Optional of User.
     */
    Optional<User> findByEmail(String email);

    /**
     * This method finds a user by auth token.
     * It returns an Optional of User.
     *
     * @param authToken The auth token of the user.
     * @return An Optional of User.
     */
    Optional<User> findByAuthToken(UUID authToken);

    /**
     * This method finds a user by nickname.
     * It returns an Optional of User.
     *
     * @param nickname The nickname of the user.
     * @return An Optional of User.
     */
    Optional<User> findByNickname(String nickname);
}