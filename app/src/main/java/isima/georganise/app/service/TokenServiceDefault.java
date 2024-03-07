package isima.georganise.app.service;


import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.repository.TokensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceDefault implements TokenService{
    @Autowired
    TokensRepository tokensRepository;
    @Override
    public List<Token> tokens() {
        return tokensRepository.findAll();
    }

    @Override
    public Token token(Long id) {
        Optional<Token> op = tokensRepository.findById(id);
        if(op.isPresent()) {
            return op.get();
        }
        else {
            return null;
        }
    }

    @Override
    public Token addToken(Token token) {
        return tokensRepository.save(token);
    }

    @Override
    public boolean deleteToken(Long id) {
        Optional<Token> u = tokensRepository.findById(id);
        if (u.isPresent()) {
            tokensRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Token updateToken(Long id, Token token) {
        Optional<Token> optionalToken = tokensRepository.findById(id);

        if (optionalToken.isPresent()) {
            Token existingToken = optionalToken.get();

            if(token.getToken() != null)
                existingToken.setToken(token.getToken());
            if(token.getCreator() != null)
                existingToken.setCreator(token.getCreator());
            if(token.getAccessRight() != null)
                existingToken.setAccessRight(token.getAccessRight());

            return tokensRepository.save(existingToken);
        } else {
            return null;
        }
    }
}

