package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.GetUserNicknameDTO;
import isima.georganise.app.entity.dto.UserCreationDTO;
import isima.georganise.app.entity.dto.UserLoginDTO;
import isima.georganise.app.entity.dto.UserUpdateDTO;
import isima.georganise.app.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This class is responsible for handling all the user related requests.
 * It uses the @RestController annotation to indicate that the data returned by each method will be written straight into the response body instead of rendering a template.
 */
@RestController
@RequestMapping("/api/user")
public class UsersController {

    private static final String AUTH_TOKEN_COOKIE_NAME = "authToken";
    private final UserService userService;

    /**
     * Constructor for the UsersController class.
     * @param userService the user service
     */
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users.
     * @param authToken the authentication token
     * @return ResponseEntity with all users
     */
    @GetMapping(path = "", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<User>> getUsers(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        System.out.println("UsersController.getUsers: ");
        return ResponseEntity.ok(userService.getAllUsers(authToken));
    }

    /**
     * Get username by id.
     * @param authToken the authentication token
     * @param id the user id
     * @return ResponseEntity with the username
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<GetUserNicknameDTO> getUserNameById(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("UsersController.getUserNameById: " + id);
        return ResponseEntity.ok(userService.getUserById(authToken, id));
    }

    /**
     * Get the current logged-in user.
     * @param authToken the authentication token
     * @return ResponseEntity with the user
     */
    @GetMapping(path = "/me", produces = "application/json")
    public @NotNull ResponseEntity<User> getMe(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        System.out.println("UsersController.getMe: ");
        return ResponseEntity.ok(userService.getMe(authToken));
    }

    /**
     * Create a new user.
     * @param user the user to be created
     * @param response the HttpServletResponse
     * @return ResponseEntity with the status
     */
    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Void> createUser(@RequestBody @NotNull UserCreationDTO user, @NotNull HttpServletResponse response) {
        System.out.println("UsersController.createUser: " + user);

        Cookie cookie = new Cookie(AUTH_TOKEN_COOKIE_NAME, userService.createUser(user).toString());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    /**
     * Update a user.
     * @param authToken the authentication token
     * @param id the user id
     * @param user the user to be updated
     * @return ResponseEntity with the updated user
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<User> updateUser(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody @NotNull UserUpdateDTO user) {
        System.out.println("UsersController.updateUser: " + id + " " + user);
        return ResponseEntity.ok(userService.updateUser(authToken, id, user));
    }

    /**
     * Delete a user.
     * @param authToken the authentication token
     * @param id the user id
     */
    @DeleteMapping(path = "/{id}")
    public @NotNull ResponseEntity<Void> deleteUser(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("UsersController.deleteUser: " + id);
        userService.deleteUser(authToken, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Login a user.
     * @param user the user to be logged in
     * @param response the HttpServletResponse
     * @return ResponseEntity with the status
     */
    @PostMapping(path = "/login", consumes = "application/json")
    public @NotNull ResponseEntity<Void> login(@RequestBody @NotNull UserLoginDTO user, @NotNull HttpServletResponse response) {
        System.out.println("UsersController.login: " + user);

        Cookie cookie = new Cookie(AUTH_TOKEN_COOKIE_NAME, userService.login(user).toString());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    /**
     * Logout a user.
     * @param authToken the authentication token
     * @param response the HttpServletResponse
     * @return ResponseEntity with the status
     */
    @PostMapping(path = "/logout")
    public @NotNull ResponseEntity<Void> logout(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @NotNull HttpServletResponse response) {
        System.out.println("UsersController.logout: ");
        userService.logout(authToken);

        Cookie cookie = new Cookie(AUTH_TOKEN_COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}