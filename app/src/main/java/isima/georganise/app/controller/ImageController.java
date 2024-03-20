package isima.georganise.app.controller;

import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import isima.georganise.app.service.image.ImageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This class is responsible for handling all the image related requests.
 * It uses the @RestController annotation to indicate that the data returned by each method will be written straight into the response body instead of rendering a template.
 */
@RestController
@RequestMapping("/api/image")
public class ImageController {

    private static final String AUTH_TOKEN_COOKIE_NAME = "authToken";
    private final @NotNull ImageService imageService;

    /**
     * Constructor for the ImageController class.
     * @param imageService the image service
     */
    @Autowired
    public ImageController(@NotNull ImageService imageService) {
        Assert.notNull(imageService, "ImageService must not be null");
        this.imageService = imageService;
    }

    /**
     * Get all images.
     * @param authToken the authentication token
     * @return ResponseEntity with all images
     */
    @GetMapping(path = "", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<ImageDTO>> getAllImages(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        System.out.println("ImageController.getAllImages: ");
        return ResponseEntity.ok(imageService.getAllImages(authToken));
    }

    /**
     * Get image by id.
     * @param authToken the authentication token
     * @param id the image id
     * @return ResponseEntity with the image
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<ImageDTO> getImageById(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("ImageController.getImageById: " + id);
        return ResponseEntity.ok(imageService.getImageById(authToken, id));
    }

    /**
     * Get images by keyword.
     * @param authToken the authentication token
     * @param keyword the keyword
     * @return ResponseEntity with the images
     */
    @GetMapping(path = "/keyword/{keyword}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<ImageDTO>> getImagesByKeyword(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("keyword") String keyword) {
        System.out.println("ImageController.getImagesByKeyword: " + keyword);
        return ResponseEntity.ok(imageService.getImageByKeyword(authToken, keyword));
    }

    /**
     * Create an image.
     * @param authToken the authentication token
     * @param image the image to be created
     * @return ResponseEntity with the created image
     */
    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<ImageDTO> createImage(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @RequestBody @NotNull ImageCreationDTO image) {
        System.out.println("ImageController.createImage: " + image);
        return ResponseEntity.ok(imageService.createImage(authToken, image));
    }

    /**
     * Update an image.
     * @param authToken the authentication token
     * @param id the image id
     * @param image the image to be updated
     * @return ResponseEntity with the updated image
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<ImageDTO> updateImage(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody @NotNull ImageUpdateDTO image) {
        System.out.println("ImageController.updateImage: " + id + ": " + image);
        return ResponseEntity.ok(imageService.updateImage(authToken, id, image));
    }

    /**
     * Delete an image.
     * @param authToken the authentication token
     * @param id the image id
     */
    @DeleteMapping(path = "/{id}")
    public @NotNull ResponseEntity<Void> deleteImage(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("ImageController.deleteImage: " + id);
        imageService.deleteImage(authToken, id);
        return ResponseEntity.ok().build();
    }
}