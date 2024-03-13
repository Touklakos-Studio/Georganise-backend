package isima.georganise.app.service.token;

import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.dto.TokenUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TokenService {

    public List<Token> getAllTokens();

    public Token getTokenById(Long id);

    public Iterable<Token> getTokensByUser(Long id);

    public Iterable<Token> getTokensByTag(Long id);

    public Token createToken(TokenCreationDTO token);

    public void deleteToken(Long id);

    public Token updateToken(Long id, TokenUpdateDTO token);

    public Token addTokenToUser(Long id);

}
