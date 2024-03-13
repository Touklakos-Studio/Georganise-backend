package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import isima.georganise.app.entity.dto.ImageUpdateDTO;
import isima.georganise.app.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<Iterable<Image>> getImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Image> getImageById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<List<Image>> getImagesByKeyword(@PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(imageService.getImageByKeyword(keyword));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Image> createImage(@RequestBody ImageCreationDTO image) {
        return ResponseEntity.ok(imageService.createImage(image));
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Image> updateImage(@PathVariable("id") Long id, @RequestBody ImageUpdateDTO image) {
        return ResponseEntity.ok(imageService.updateImage(id, image));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }
}
