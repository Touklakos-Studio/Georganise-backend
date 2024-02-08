package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
public class TokenController {


    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenById(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping(path="/user/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenByUser(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping(path="/tag/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenByTag(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping(path="/", produces = "application/json")
    public ResponseEntity<Token> generateToken() {
        return null;
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable("id") Long id) {
        return null;
    }

}
