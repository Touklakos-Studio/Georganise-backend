package isima.georganise.app.service.token;

import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.dto.TokenUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing tokens.
 */
@Service
public interface TokenService {

    /**
     * Retrieves all tokens associated with the given authentication token.
     *
     * @param authToken The authentication token.
     * @return A list of tokens.
     */
    List<Token> getAllTokens(UUID authToken);

    /**
     * Retrieves a token by its ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the token.
     * @return The token.
     */
    Token getTokenById(UUID authToken, Long id);

    /**
     * Retrieves tokens associated with a user by the user's ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the user.
     * @param isCreator A flag indicating whether the user is the creator of the tokens.
     * @return An iterable of tokens.
     */
    Iterable<Token> getTokensByUser(UUID authToken, Long id, boolean isCreator);

    /**
     * Retrieves tokens associated with a tag by the tag's ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the tag.
     * @return An iterable of tokens.
     */
    Iterable<Token> getTokensByTag(UUID authToken, Long id);

    /**
     * Creates a new token with the given authentication token and token creation DTO.
     *
     * @param authToken The authentication token.
     * @param token The token creation DTO.
     * @return The created token.
     */
    Token createToken(UUID authToken, TokenCreationDTO token);

    /**
     * Deletes a token by its ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the token.
     */
    void deleteToken(UUID authToken, Long id);

    /**
     * Updates a token by its ID, the given authentication token, and token update DTO.
     *
     * @param authToken The authentication token.
     * @param id The ID of the token.
     * @param token The token update DTO.
     * @return The updated token.
     */
    Token updateToken(UUID authToken, Long id, TokenUpdateDTO token);

    /**
     * Adds a token to a user by the user's ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param token The ID of the token.
     * @return The token.
     */
    Token addTokenToUser(UUID authToken, UUID token);
}