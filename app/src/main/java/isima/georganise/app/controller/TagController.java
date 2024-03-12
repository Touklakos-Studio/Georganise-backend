package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.exception.UnimplementedException;
import isima.georganise.app.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<Tag> getTagByKeyword(@PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(tagService.getTagByKeyword(keyword));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.addTag(tag));
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") Long id, @RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.updateTag(id, tag));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
