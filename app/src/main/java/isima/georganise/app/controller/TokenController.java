package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.dto.TokenUpdateDTO;
import isima.georganise.app.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping(path="", produces = "application/json")
    public ResponseEntity<Iterable<Token>> getTokens() {
        return ResponseEntity.ok(tokenService.getAllTokens());
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tokenService.getTokenById(id));
    }

    @GetMapping(path="/user/{id}", produces = "application/json")
    public ResponseEntity<Iterable<Token>> getTokensByUser(@PathVariable("id") Long id) {
        return  ResponseEntity.ok(tokenService.getTokensByUser(id));
    }

    @GetMapping(path="/tag/{id}", produces = "application/json")
    public ResponseEntity<Iterable<Token>> getTokensByTag(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tokenService.getTokensByTag(id));
    }

    @GetMapping(path="/new", produces = "application/json")
    public ResponseEntity<Token> generateToken(@RequestBody TokenCreationDTO token) {
        return  ResponseEntity.ok(tokenService.createToken(token));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable("id") Long id) {
        tokenService.deleteToken(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Token> updateToken(@PathVariable("id") Long id, @RequestBody TokenUpdateDTO token) {
        return ResponseEntity.ok(tokenService.updateToken(id, token));
    }

    @PatchMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Token> addTokenToUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tokenService.addTokenToUser(id));
    }

}
