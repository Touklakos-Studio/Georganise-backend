package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<Tag> getTagByKeyword(@PathVariable("keyword") String keyword) {
        return null;
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        return null;
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") Long id, @RequestBody Tag tag) {
        return null;
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        return null;
    }
}
