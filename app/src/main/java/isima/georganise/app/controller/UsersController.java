package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.UserCreationDTO;
import isima.georganise.app.entity.dto.UserLoginDTO;
import isima.georganise.app.entity.dto.UserUpdateDTO;
import isima.georganise.app.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping(path="", produces = "application/json")
    public ResponseEntity<Iterable<User>> getUsers(@CookieValue("authToken") UUID authToken) {
        return ResponseEntity.ok(userService.getAllUsers(authToken));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(authToken, id));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> createUser(@RequestBody UserCreationDTO user, HttpServletResponse response) {

        Cookie cookie = new Cookie("authToken", userService.createUser(user).toString());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> updateUser(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id, @RequestBody UserUpdateDTO user) {
        return ResponseEntity.ok(userService.updateUser(authToken, id, user));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteUser(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        userService.deleteUser(authToken, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path="/login", consumes = "application/json")
    public ResponseEntity<Void> login(@RequestBody UserLoginDTO user, HttpServletResponse response) {

        Cookie cookie = new Cookie("authToken", userService.login(user).toString());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path="/logout")
    public ResponseEntity<Void> logout(@CookieValue("authToken") UUID authToken, HttpServletResponse response) {
        userService.logout(authToken);

        Cookie cookie = new Cookie("authToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}
