package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Image;
import isima.georganise.app.exception.UnimplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Image> getImageById(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<List<Image>> getImagesByKeyword(@PathVariable("keyword") String keyword) {
        throw new UnimplementedException();
    }

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        throw new UnimplementedException();
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Image> updateImage(@PathVariable("id") Long id, @RequestBody Image image) {
        throw new UnimplementedException();
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }
}
