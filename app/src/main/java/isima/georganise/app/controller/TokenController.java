package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.exception.UnimplementedException;
import isima.georganise.app.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tokenService.getTokenById(id));
    }

    @GetMapping(path="/user/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenByUser(@PathVariable("id") Long id) {
        return  ResponseEntity.ok(tokenService.getTokenByUser(id));
    }

    @GetMapping(path="/tag/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenByTag(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tokenService.getTokenByTag(id));
    }

    @GetMapping(path="/", produces = "application/json")
    public ResponseEntity<Token> generateToken() {
        return  ResponseEntity.ok(tokenService.addToken(new Token()));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable("id") Long id) {
        tokenService.deleteToken(id);
        return ResponseEntity.noContent().build();
    }

}
