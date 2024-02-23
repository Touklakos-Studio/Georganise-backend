package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UsersController {

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return null;
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return null;
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        return null;
    }
}
