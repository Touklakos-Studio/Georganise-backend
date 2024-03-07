package isima.georganise.app.service;

import isima.georganise.app.entity.dao.Token;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TokenService {
    public List<Token> tokens();
    public Token token(Long id);
    public Token addToken(Token token);
    public boolean deleteToken(Long id);
    public Token updateToken(Long id,Token token);
}
