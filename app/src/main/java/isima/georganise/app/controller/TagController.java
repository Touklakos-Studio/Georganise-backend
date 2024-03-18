package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dto.RemovePlaceFromTagDTO;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TagUpdateDTO;
import isima.georganise.app.service.tag.TagService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private static final String AUTH_TOKEN_COOKIE_NAME = "authToken";
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(path = "", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Tag>> getTags(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        System.out.println("TagController.getTags: ");
        return ResponseEntity.ok(tagService.getAllTags(authToken));
    }

    @GetMapping(path = "/keyword/{keyword}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Tag>> getTagsByKeyword(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("keyword") String keyword) {
        System.out.println("TagController.getTagsByKeyword: " + keyword);
        return ResponseEntity.ok(tagService.getTagsByKeyword(authToken, keyword));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Tag> getTagById(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TagController.getTagById: " + id);
        return ResponseEntity.ok(tagService.getTagById(authToken, id));
    }

    @GetMapping(path = "/placeTag/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Tag> getTagByPlaceId(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TagController.getTagByPlaceId: " + id);
        return ResponseEntity.ok(tagService.getTagByPlaceId(authToken, id));
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Tag> createTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @RequestBody TagCreationDTO tag) {
        System.out.println("TagController.createTag: " + tag.toString());
        return ResponseEntity.ok(tagService.createTag(authToken, tag));
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Tag> updateTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody TagUpdateDTO tag) {
        System.out.println("TagController.updateTag: " + id + " " + tag.toString());
        return ResponseEntity.ok(tagService.updateTag(authToken, id, tag));
    }

    @DeleteMapping(path = "/{id}")
    public @NotNull ResponseEntity<Void> deleteTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TagController.deleteTag: " + id);
        tagService.deleteTag(authToken, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/removeFrom/{id}")
    public @NotNull ResponseEntity<Void> removePlaceFromTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody RemovePlaceFromTagDTO place) {
        System.out.println("TagController.removePlaceFromTag: " + id + " " + place.toString());
        tagService.removePlaceFromTag(authToken, id, place);
        return ResponseEntity.ok().build();
    }
}
