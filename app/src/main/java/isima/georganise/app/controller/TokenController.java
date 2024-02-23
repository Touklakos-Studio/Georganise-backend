package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.exception.UnimplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
public class TokenController {


    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenById(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }

    @GetMapping(path="/user/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenByUser(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }

    @GetMapping(path="/tag/{id}", produces = "application/json")
    public ResponseEntity<Token> getTokenByTag(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }

    @GetMapping(path="/", produces = "application/json")
    public ResponseEntity<Token> generateToken() {
        throw new UnimplementedException();
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }

}
