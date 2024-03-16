package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import isima.georganise.app.service.image.ImageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private static final String AUTH_TOKEN_COOKIE_NAME = "authToken";
    private final @NotNull ImageService imageService;

    @Autowired
    public ImageController(@NotNull ImageService imageService) {
        Assert.notNull(imageService, "ImageService must not be null");
        this.imageService = imageService;
    }

    @GetMapping(path = "", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Image>> getImages(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        return ResponseEntity.ok(imageService.getAllImages(authToken));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Image> getImageById(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(imageService.getImageById(authToken, id));
    }

    @GetMapping(path = "/keyword/{keyword}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Image>> getImagesByKeyword(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(imageService.getImageByKeyword(authToken, keyword));
    }

    @PostMapping(path = "", consumes = "multipart/form-data", produces = "application/json")
    public @NotNull ResponseEntity<Image> createImage(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @ModelAttribute ImageCreationDTO image) {
        return ResponseEntity.ok(imageService.createImage(authToken, image));
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Image> updateImage(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody ImageUpdateDTO image) {
        return ResponseEntity.ok(imageService.updateImage(authToken, id, image));
    }

    @DeleteMapping(path = "/{id}")
    public @NotNull ResponseEntity<Void> deleteImage(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        imageService.deleteImage(authToken, id);
        return ResponseEntity.ok().build();
    }
}
