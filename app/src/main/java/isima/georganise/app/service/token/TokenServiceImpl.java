package isima.georganise.app.service.token;

import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.dto.TokenUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.exception.NotLoggedException;
import isima.georganise.app.exception.UnauthorizedException;
import isima.georganise.app.repository.TagsRepository;
import isima.georganise.app.repository.TokensRepository;
import isima.georganise.app.repository.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service class for managing tokens.
 * Implements the TokenService interface.
 */
@Service
public class TokenServiceImpl implements TokenService {

    private final @NotNull TokensRepository tokensRepository;
    private final @NotNull UsersRepository usersRepository;
    private final @NotNull TagsRepository tagsRepository;

    /**
     * Constructor for the TokenServiceImpl class.
     *
     * @param usersRepository The repository for users.
     * @param tokensRepository The repository for tokens.
     * @param tagsRepository The repository for tags.
     */
    @Autowired
    public TokenServiceImpl(@NotNull UsersRepository usersRepository, @NotNull TokensRepository tokensRepository, @NotNull TagsRepository tagsRepository) {
        Assert.notNull(tokensRepository, "Tokens repository must not be null");
        Assert.notNull(usersRepository, "Users repository must not be null");
        Assert.notNull(tagsRepository, "Tags repository must not be null");
        this.usersRepository = usersRepository;
        this.tokensRepository = tokensRepository;
        this.tagsRepository = tagsRepository;
    }

    /**
     * Retrieves all tokens associated with the given authentication token.
     *
     * @param authToken The authentication token.
     * @return A list of tokens.
     */
    @Override
    public @NotNull List<Token> getAllTokens(UUID authToken) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return (List<Token>) tokensRepository.findByUserId(currentUser.getUserId()).orElse(new ArrayList<>());
    }

    /**
     * Retrieves a token by its ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the token.
     * @return The token.
     */
    @Override
    public @NotNull Token getTokenById(UUID authToken, @NotNull Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Token token = tokensRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!token.getUserId().equals(currentUser.getUserId())) throw new NotFoundException("Token not found");

        return token;
    }

    /**
     * Retrieves tokens associated with a tag by the tag's ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the tag.
     * @return An iterable of tokens.
     */
    @Override
    public @NotNull Iterable<Token> getTokensByTag(UUID authToken, @NotNull Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        Tag tag = tagsRepository.findById(id).orElseThrow(NotFoundException::new);

        List<Token> tokens = tag.getUserId().equals(currentUser.getUserId()) ?
                tokensRepository.findByCreatorIdAndTagId(currentUser.getUserId(), id) :
                tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), id);

        if (tokens.isEmpty()) throw new NotFoundException("No tokens found for tag " + id);

        return tokens;
    }

    /**
     * Creates a new token with the given authentication token and token creation DTO.
     *
     * @param authToken The authentication token.
     * @param token The token creation DTO.
     * @return The created token.
     */
    @Override
    public @NotNull Token createToken(UUID authToken, @NotNull TokenCreationDTO token) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (Objects.isNull(token.getAccessRight())) throw new IllegalArgumentException("Access right is required");
        if (Objects.isNull(token.getTagId())) throw new IllegalArgumentException("Tag id is required");

        User targetUser = !Objects.isNull(token.getNickname()) ?
                usersRepository.findByNickname(token.getNickname()).orElseThrow(NotFoundException::new) :
                null;

        Tag tag = tagsRepository.findById(token.getTagId()).orElseThrow(NotFoundException::new);

        if (!tag.getUserId().equals(currentUser.getUserId())) throw new UnauthorizedException(currentUser.getNickname(), "create token for tag " + tag.getTagId());

        return tokensRepository.saveAndFlush(new Token(token, currentUser.getUserId(), Objects.isNull(targetUser) ? null : targetUser.getUserId()));
    }

    /**
     * Deletes a token by its ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the token.
     */
    @Override
    public void deleteToken(UUID authToken, @NotNull Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Token token = tokensRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!token.getCreatorId().equals(currentUser.getUserId())) throw new UnauthorizedException(currentUser.getNickname(), "delete token " + id);

        tokensRepository.delete(token);
    }

    /**
     * Updates a token by its ID, the given authentication token, and token update DTO.
     *
     * @param authToken The authentication token.
     * @param id The ID of the token.
     * @param token The token update DTO.
     * @return The updated token.
     */
    @Override
    public @NotNull Token updateToken(UUID authToken, @NotNull Long id, @NotNull TokenUpdateDTO token) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Token tokenToUpdate = tokensRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!tokenToUpdate.getCreatorId().equals(currentUser.getUserId())) throw new UnauthorizedException(currentUser.getNickname(), "update token " + id);

        if (token.getAccessRight() != null) tokenToUpdate.setAccessRight(token.getAccessRight());

        return tokensRepository.saveAndFlush(tokenToUpdate);
    }

    /**
     * Adds a token to a user by the user's ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param token The ID of the token.
     * @return The token.
     */
    @Override
    public @NotNull Token addTokenToUser(UUID authToken, @NotNull UUID token) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Token tokenToUpdate = tokensRepository.findByTokenValue(token).orElseThrow(NotFoundException::new);

        if (tokenToUpdate.getUserId() != null) throw new IllegalArgumentException("Token already has a user assigned");

        tokenToUpdate.setUserId(currentUser.getUserId());

        return tokensRepository.saveAndFlush(tokenToUpdate);
    }

    /**
     * Retrieves tokens associated with a user by the user's ID and the given authentication token.
     *
     * @param authToken The authentication token.
     * @param id The ID of the user.
     * @param isCreator A flag indicating whether the user is the creator of the tokens.
     * @return An iterable of tokens.
     */
    @Override
    public @NotNull Iterable<Token> getTokensByUser(UUID authToken, Long id, boolean isCreator) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (!currentUser.getUserId().equals(id)) throw new UnauthorizedException(currentUser.getNickname(), "get tokens of user " + id);

        Iterable<Token> tokens = isCreator ?
                tokensRepository.findByCreatorId(id).orElseThrow(NotFoundException::new) :
                tokensRepository.findByUserId(id).orElseThrow(NotFoundException::new);

        if (!tokens.iterator().hasNext()) throw new NotFoundException("No tokens found for user " + id);

        return tokens;
    }
}