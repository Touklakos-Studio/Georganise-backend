package isima.georganise.app.service.user;

import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.GetUserNicknameDTO;
import isima.georganise.app.entity.dto.UserCreationDTO;
import isima.georganise.app.entity.dto.UserLoginDTO;
import isima.georganise.app.entity.dto.UserUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing users.
 */
@Service
public interface UserService {

    /**
     * Retrieves all users associated with the given authentication token.
     *
     * @param authToken The authentication token.
     * @return A list of users.
     */
    List<User> getAllUsers(UUID authToken);

    /**
     * Retrieves a user by its ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the user.
     * @return The user's nickname.
     */
    GetUserNicknameDTO getUserById(UUID authToken, Long id);

    /**
     * Creates a new user with the given user creation DTO.
     *
     * @param user The user creation DTO.
     * @return The authentication token of the created user.
     */
    UUID createUser(UserCreationDTO user);

    /**
     * Deletes a user by its ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the user.
     */
    void deleteUser(UUID authToken, Long id);

    /**
     * Updates a user by its ID, the given authentication token, and user update DTO.
     *
     * @param authToken The authentication token.
     * @param id The ID of the user.
     * @param user The user update DTO.
     * @return The updated user.
     */
    User updateUser(UUID authToken, Long id, UserUpdateDTO user);

    /**
     * Logs in a user with the given user login DTO.
     *
     * @param user The user login DTO.
     * @return The authentication token of the logged-in user.
     */
    UUID login(UserLoginDTO user);

    /**
     * Logs out a user with the given authentication token.
     *
     * @param authToken The authentication token.
     */
    void logout(UUID authToken);

    /**
     * Retrieves the currently authenticated user.
     *
     * @param authToken The authentication token.
     * @return The authenticated user.
     */
    User getMe(UUID authToken);
}