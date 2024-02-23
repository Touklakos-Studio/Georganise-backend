package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.exception.UnimplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<Tag> getTagByKeyword(@PathVariable("keyword") String keyword) {
        throw new UnimplementedException();
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        throw new UnimplementedException();
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") Long id, @RequestBody Tag tag) {
        throw new UnimplementedException();
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }
}
