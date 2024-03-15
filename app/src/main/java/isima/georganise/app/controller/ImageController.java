package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import isima.georganise.app.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<Iterable<Image>> getImages(@CookieValue("authToken") UUID authToken) {
        return ResponseEntity.ok(imageService.getAllImages(authToken));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Image> getImageById(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(imageService.getImageById(authToken, id));
    }

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<Iterable<Image>> getImagesByKeyword(@CookieValue("authToken") UUID authToken, @PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(imageService.getImageByKeyword(authToken, keyword));
    }

    @PostMapping(path="", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<Image> createImage(@CookieValue("authToken") UUID authToken, @ModelAttribute ImageCreationDTO image) {
        return ResponseEntity.ok(imageService.createImage(authToken, image));
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Image> updateImage(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id, @RequestBody ImageUpdateDTO image) {
        return ResponseEntity.ok(imageService.updateImage(authToken, id, image));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteImage(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        imageService.deleteImage(authToken, id);
        return ResponseEntity.ok().build();
    }
}
