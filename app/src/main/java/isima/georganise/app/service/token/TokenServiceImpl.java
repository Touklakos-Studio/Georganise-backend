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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    TokensRepository tokensRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TagsRepository tagsRepository;

    @Override
    public List<Token> getAllTokens(UUID authToken) {
        usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return tokensRepository.findAll();
    }

    @Override
    public Token getTokenById(UUID authToken, Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Token token =  tokensRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!token.getUserId().equals(currentUser.getUserId()))
            throw new NotFoundException("Token not found");

        return token;
    }

    @Override
    public Iterable<Token> getTokensByUser(UUID authToken, Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (!currentUser.getUserId().equals(id))
            throw new UnauthorizedException(currentUser.getNickname(), "get tokens of user " + id);

        return tokensRepository.findByUserId(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Iterable<Token> getTokensByTag(UUID authToken, Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return tokensRepository.findByTagIdAndCreatorId(id, currentUser.getUserId()).orElseThrow(NotFoundException::new);
    }

    @Override
    public Token createToken(UUID authToken, TokenCreationDTO token) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (Objects.isNull(token.getAccessRight()))
            throw new IllegalArgumentException("Access right is required");
        if (Objects.isNull(token.getTagId()))
            throw new IllegalArgumentException("Tag id is required");

        Tag tag = tagsRepository.findById(token.getTagId()).orElseThrow(NotFoundException::new);

        if (!tag.getUserId().equals(currentUser.getUserId()))
            throw new UnauthorizedException(currentUser.getNickname(), "create token for tag " + tag.getTagId());

        return tokensRepository.saveAndFlush(new Token(token, currentUser.getUserId()));
    }

    @Override
    public void deleteToken(UUID authToken, Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Token token = tokensRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!token.getCreatorId().equals(currentUser.getUserId()))
            throw new UnauthorizedException(currentUser.getNickname(), "delete token " + id);

        tokensRepository.delete(token);
    }

    @Override
    public Token updateToken(UUID authToken, Long id, TokenUpdateDTO token) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Token tokenToUpdate = tokensRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!tokenToUpdate.getCreatorId().equals(currentUser.getUserId()))
            throw new UnauthorizedException(currentUser.getNickname(), "update token " + id);

        if (token.getAccessRight() != null)
            tokenToUpdate.setAccessRight(token.getAccessRight());
        if (token.getUserId() != null)
            tokenToUpdate.setUserId(token.getUserId());

        return tokensRepository.saveAndFlush(tokenToUpdate);
    }

    @Override
    public Token addTokenToUser(UUID authToken, Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Token tokenToUpdate = tokensRepository.findById(id).orElseThrow(NotFoundException::new);

        if (tokenToUpdate.getUserId() != null)
            throw new IllegalArgumentException("Token already has a user assigned");

        tokenToUpdate.setUserId(currentUser.getUserId());

        return tokensRepository.saveAndFlush(tokenToUpdate);
    }
}

