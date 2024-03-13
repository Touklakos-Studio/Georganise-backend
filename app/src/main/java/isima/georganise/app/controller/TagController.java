package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TagUpdateDTO;
import isima.georganise.app.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(path="", produces = "application/json")
    public ResponseEntity<Iterable<Tag>> getTags(@CookieValue("authToken") UUID authToken) {
        return ResponseEntity.ok(tagService.getAllTags(authToken));
    }

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<Iterable<Tag>> getTagsByKeyword(@CookieValue("authToken") UUID authToken, @PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(tagService.getTagsByKeyword(authToken, keyword));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Tag> getTagById(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(tagService.getTagById(authToken, id));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tag> createTag(@CookieValue("authToken") UUID authToken, @RequestBody TagCreationDTO tag) {
        return ResponseEntity.ok(tagService.createTag(authToken, tag));
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tag> updateTag(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id, @RequestBody TagUpdateDTO tag) {
        return ResponseEntity.ok(tagService.updateTag(authToken, id, tag));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteTag(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        tagService.deleteTag(authToken, id);
        return ResponseEntity.ok().build();
    }
}
