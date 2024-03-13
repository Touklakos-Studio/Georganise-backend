package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.dto.TokenUpdateDTO;
import isima.georganise.app.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping(path="", produces = "application/json")
    public ResponseEntity<Iterable<Token>> getTokens(@CookieValue("authToken") UUID authToken) {
        return ResponseEntity.ok(tokenService.getAllTokens(authToken));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenById(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(tokenService.getTokenById(authToken, id));
    }

    @GetMapping(path="/user/{id}", produces = "application/json")
    public ResponseEntity<Iterable<Token>> getTokensByUser(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return  ResponseEntity.ok(tokenService.getTokensByUser(authToken, id));
    }

    @GetMapping(path="/tag/{id}", produces = "application/json")
    public ResponseEntity<Iterable<Token>> getTokensByTag(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(tokenService.getTokensByTag(authToken, id));
    }

    @GetMapping(path="/new", produces = "application/json")
    public ResponseEntity<Token> generateToken(@CookieValue("authToken") UUID authToken, @RequestBody TokenCreationDTO token) {
        return  ResponseEntity.ok(tokenService.createToken(authToken, token));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteToken(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        tokenService.deleteToken(authToken, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Token> updateToken(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id, @RequestBody TokenUpdateDTO token) {
        return ResponseEntity.ok(tokenService.updateToken(authToken, id, token));
    }

    @PatchMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Token> addTokenToUser(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(tokenService.addTokenToUser(authToken, id));
    }

}
