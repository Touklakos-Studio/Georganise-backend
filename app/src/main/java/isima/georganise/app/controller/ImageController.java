package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Image> getImageById(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<List<Image>> getImagesByKeyword(@PathVariable("keyword") String keyword) {
        return null;
    }

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        return null;
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Image> updateImage(@PathVariable("id") Long id, @RequestBody Image image) {
        return null;
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
        return null;
    }
}
