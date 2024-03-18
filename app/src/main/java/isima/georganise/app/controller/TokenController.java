package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.dto.TokenUpdateDTO;
import isima.georganise.app.service.token.TokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private static final String AUTH_TOKEN_COOKIE_NAME = "authToken";
    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping(path = "", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Token>> getTokens(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        System.out.println("TokenController.getTokens: ");
        return ResponseEntity.ok(tokenService.getAllTokens(authToken));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Token> getTokenById(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.getTokenById: " + id);
        return ResponseEntity.ok(tokenService.getTokenById(authToken, id));
    }

    @GetMapping(path = "/user/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Token>> getTokensByUser(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.getTokensByUser: " + id);
        return ResponseEntity.ok(tokenService.getTokensByUser(authToken, id, false));
    }

    @GetMapping(path = "/creator/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Token>> getTokensByCreator(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.getTokensByCreator: " + id);
        return ResponseEntity.ok(tokenService.getTokensByUser(authToken, id, true));
    }

    @GetMapping(path = "/tag/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Token>> getTokensByTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.getTokensByTag: " + id);
        return ResponseEntity.ok(tokenService.getTokensByTag(authToken, id));
    }

    @PostMapping(path = "/new", produces = "application/json", consumes = "application/json")
    public @NotNull ResponseEntity<Token> generateToken(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @RequestBody TokenCreationDTO token) {
        System.out.println("TokenController.generateToken: " + token.toString());
        return ResponseEntity.ok(tokenService.createToken(authToken, token));
    }

    @DeleteMapping(path = "/{id}")
    public @NotNull ResponseEntity<Void> deleteToken(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.deleteToken: " + id);
        tokenService.deleteToken(authToken, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Token> updateToken(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody TokenUpdateDTO token) {
        System.out.println("TokenController.updateToken: " + id + " " + token.toString());
        return ResponseEntity.ok(tokenService.updateToken(authToken, id, token));
    }

    @PatchMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Token> addTokenToUser(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") UUID token) {
        System.out.println("TokenController.addTokenToUser: " + token);
        return ResponseEntity.ok(tokenService.addTokenToUser(authToken, token));
    }

}
