package isima.georganise.app.service.token;

import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.dto.TokenUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TokenService {

    public List<Token> getAllTokens(UUID authToken);

    public Token getTokenById(UUID authToken, Long id);

    public Iterable<Token> getTokensByUser(UUID authToken, Long id);

    public Iterable<Token> getTokensByTag(UUID authToken, Long id);

    public Token createToken(UUID authToken, TokenCreationDTO token);

    public void deleteToken(UUID authToken, Long id);

    public Token updateToken(UUID authToken, Long id, TokenUpdateDTO token);

    public Token addTokenToUser(UUID authToken, Long id);

}
