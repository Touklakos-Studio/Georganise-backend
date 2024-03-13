package isima.georganise.app.service.token;


import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.dto.TokenUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.repository.TokensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    TokensRepository tokensRepository;

    @Override
    public List<Token> getAllTokens() {
        return tokensRepository.findAll();
    }

    @Override
    public Token getTokenById(Long id) {
        return tokensRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Iterable<Token> getTokensByUser(Long id) {
        return tokensRepository.findByUserId(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Iterable<Token> getTokensByTag(Long id) {
        return tokensRepository.findByTagId(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Token createToken(TokenCreationDTO token) {
        return tokensRepository.saveAndFlush(new Token(token));
    }

    @Override
    public void deleteToken(Long id) {
        tokensRepository.delete(tokensRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Token updateToken(Long id, TokenUpdateDTO token) {
        Token tokenToUpdate = tokensRepository.findById(id).orElseThrow(NotFoundException::new);

        if (token.getAccessRight() != null)
            tokenToUpdate.setAccessRight(token.getAccessRight());
        if (token.getUserId() != null)
            tokenToUpdate.setUserId(token.getUserId());

        return tokensRepository.saveAndFlush(tokenToUpdate);
    }

    @Override
    public Token addTokenToUser(Long id) {
        Token tokenToUpdate = tokensRepository.findById(id).orElseThrow(NotFoundException::new);

        tokenToUpdate.setUserId(1L);

        return tokensRepository.saveAndFlush(tokenToUpdate);
    }
}

