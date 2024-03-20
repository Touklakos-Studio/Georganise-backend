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

/**
 * This class is responsible for handling all the tag related requests.
 * It uses the @RestController annotation to indicate that the data returned by each method will be written straight into the response body instead of rendering a template.
 */
@RestController
@RequestMapping("/api/tag")
public class TagController {

    private static final String AUTH_TOKEN_COOKIE_NAME = "authToken";
    private final TagService tagService;

    /**
     * Constructor for the TagController class.
     * @param tagService the tag service
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Get all tags.
     * @param authToken the authentication token
     * @return ResponseEntity with all tags
     */
    @GetMapping(path = "", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Tag>> getTags(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        System.out.println("TagController.getTags: ");
        return ResponseEntity.ok(tagService.getAllTags(authToken));
    }

    /**
     * Get tags by keyword.
     * @param authToken the authentication token
     * @param keyword the keyword
     * @return ResponseEntity with the tags
     */
    @GetMapping(path = "/keyword/{keyword}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Tag>> getTagsByKeyword(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("keyword") String keyword) {
        System.out.println("TagController.getTagsByKeyword: " + keyword);
        return ResponseEntity.ok(tagService.getTagsByKeyword(authToken, keyword));
    }

    /**
     * Get tag by id.
     * @param authToken the authentication token
     * @param id the tag id
     * @return ResponseEntity with the tag
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Tag> getTagById(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TagController.getTagById: " + id);
        return ResponseEntity.ok(tagService.getTagById(authToken, id));
    }

    /**
     * Get tag by place id.
     * @param authToken the authentication token
     * @param id the place id
     * @return ResponseEntity with the tag
     */
    @GetMapping(path = "/placeTag/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Tag> getTagByPlaceId(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TagController.getTagByPlaceId: " + id);
        return ResponseEntity.ok(tagService.getTagByPlaceId(authToken, id));
    }

    /**
     * Create a tag.
     * @param authToken the authentication token
     * @param tag the tag to be created
     * @return ResponseEntity with the created tag
     */
    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Tag> createTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @RequestBody @NotNull TagCreationDTO tag) {
        System.out.println("TagController.createTag: " + tag);
        return ResponseEntity.ok(tagService.createTag(authToken, tag));
    }

    /**
     * Update a tag.
     * @param authToken the authentication token
     * @param id the tag id
     * @param tag the tag to be updated
     * @return ResponseEntity with the updated tag
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Tag> updateTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody @NotNull TagUpdateDTO tag) {
        System.out.println("TagController.updateTag: " + id + " " + tag);
        return ResponseEntity.ok(tagService.updateTag(authToken, id, tag));
    }

    /**
     * Delete a tag.
     * @param authToken the authentication token
     * @param id the tag id
     */
    @DeleteMapping(path = "/{id}")
    public @NotNull ResponseEntity<Void> deleteTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("TagController.deleteTag: " + id);
        tagService.deleteTag(authToken, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Remove a place from a tag.
     * @param authToken the authentication token
     * @param id the tag id
     * @param place the place to be removed
     */
    @DeleteMapping(path = "/removeFrom/{id}")
    public @NotNull ResponseEntity<Void> removePlaceFromTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody @NotNull RemovePlaceFromTagDTO place) {
        System.out.println("TagController.removePlaceFromTag: " + id + " " + place);
        tagService.removePlaceFromTag(authToken, id, place);
        return ResponseEntity.ok().build();
    }
}