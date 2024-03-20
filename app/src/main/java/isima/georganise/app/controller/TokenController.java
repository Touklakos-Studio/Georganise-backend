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

/**
 * This class is responsible for handling all the token related requests.
 * It uses the @RestController annotation to indicate that the data returned by each method will be written straight into the response body instead of rendering a template.
 */
@RestController
@RequestMapping("/api/token")
public class TokenController {

    private static final String AUTH_TOKEN_COOKIE_NAME = "authToken";
    private final TokenService tokenService;

    /**
     * Constructor for the TokenController class.
     * @param tokenService the token service
     */
    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Get all tokens.
     * @param authToken the authentication token
     * @return ResponseEntity with all tokens
     */
    @GetMapping(path = "", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Token>> getTokens(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        System.out.println("TokenController.getTokens: ");
        return ResponseEntity.ok(tokenService.getAllTokens(authToken));
    }

    /**
     * Get token by id.
     * @param authToken the authentication token
     * @param id the token id
     * @return ResponseEntity with the token
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Token> getTokenById(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.getTokenById: " + id);
        return ResponseEntity.ok(tokenService.getTokenById(authToken, id));
    }

    /**
     * Get tokens by user id.
     * @param authToken the authentication token
     * @param id the user id
     * @return ResponseEntity with the tokens
     */
    @GetMapping(path = "/user/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Token>> getTokensByUser(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.getTokensByUser: " + id);
        return ResponseEntity.ok(tokenService.getTokensByUser(authToken, id, false));
    }

    /**
     * Get tokens by creator id.
     * @param authToken the authentication token
     * @param id the creator id
     * @return ResponseEntity with the tokens
     */
    @GetMapping(path = "/creator/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Token>> getTokensByCreator(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.getTokensByCreator: " + id);
        return ResponseEntity.ok(tokenService.getTokensByUser(authToken, id, true));
    }

    /**
     * Get tokens by tag id.
     * @param authToken the authentication token
     * @param id the tag id
     * @return ResponseEntity with the tokens
     */
    @GetMapping(path = "/tag/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Token>> getTokensByTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.getTokensByTag: " + id);
        return ResponseEntity.ok(tokenService.getTokensByTag(authToken, id));
    }

    /**
     * Create a new token.
     * @param authToken the authentication token
     * @param token the token to be created
     * @return ResponseEntity with the created token
     */
    @PostMapping(path = "/new", produces = "application/json", consumes = "application/json")
    public @NotNull ResponseEntity<Token> generateToken(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @RequestBody @NotNull TokenCreationDTO token) {
        System.out.println("TokenController.generateToken: " + token);
        return ResponseEntity.ok(tokenService.createToken(authToken, token));
    }

    /**
     * Delete a token.
     * @param authToken the authentication token
     * @param id the token id
     */
    @DeleteMapping(path = "/{id}")
    public @NotNull ResponseEntity<Void> deleteToken(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TokenController.deleteToken: " + id);
        tokenService.deleteToken(authToken, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Update a token.
     * @param authToken the authentication token
     * @param id the token id
     * @param token the token to be updated
     * @return ResponseEntity with the updated token
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Token> updateToken(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody @NotNull TokenUpdateDTO token) {
        System.out.println("TokenController.updateToken: " + id + " " + token);
        return ResponseEntity.ok(tokenService.updateToken(authToken, id, token));
    }

    /**
     * Add a token to a user.
     * @param authToken the authentication token
     * @param token the token id
     * @return ResponseEntity with the token
     */
    @PatchMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Token> addTokenToUser(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") UUID token) {
        System.out.println("TokenController.addTokenToUser: " + token);
        return ResponseEntity.ok(tokenService.addTokenToUser(authToken, token));
    }

}