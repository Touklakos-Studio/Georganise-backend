package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.entity.dto.GetPlaceVicinityDTO;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import isima.georganise.app.entity.dto.PlaceUpdateDTO;
import isima.georganise.app.service.place.PlaceService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This class is responsible for handling all the place related requests.
 * It uses the @RestController annotation to indicate that the data returned by each method will be written straight into the response body instead of rendering a template.
 */
@RestController
@RequestMapping("/api/place")
public class PlaceController {

    private static final String AUTH_TOKEN_COOKIE_NAME = "authToken";
    private final PlaceService placeService;

    /**
     * Constructor for the PlaceController class.
     * @param placeService the place service
     */
    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * Get all places.
     * @param authToken the authentication token
     * @return ResponseEntity with all places
     */
    @GetMapping(path = "", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Place>> getPlaces(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        System.out.println("PlaceController.getPlaces: ");
        return ResponseEntity.ok(placeService.getAllPlaces(authToken));
    }

    /**
     * Get places by user id.
     * @param authToken the authentication token
     * @param id the user id
     * @return ResponseEntity with the places
     */
    @GetMapping(path = "/user/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Place>> getPlacesByUser(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("PlaceController.getPlacesByUser: " + id);
        return ResponseEntity.ok(placeService.getPlacesByUser(authToken, id));
    }

    /**
     * Get places by tag id.
     * @param authToken the authentication token
     * @param id the tag id
     * @return ResponseEntity with the places
     */
    @GetMapping(path = "/tag/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Place>> getPlacesByTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("PlaceController.getPlacesByTag: " + id);
        return ResponseEntity.ok(placeService.getPlacesByTag(authToken, id));
    }

    /**
     * Get places by keyword.
     * @param authToken the authentication token
     * @param keyword the keyword
     * @return ResponseEntity with the places
     */
    @GetMapping(path = "/keyword/{keyword}", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Place>> getPlacesByKeyword(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("keyword") String keyword) {
        System.out.println("PlaceController.getPlacesByKeyword: " + keyword);
        return ResponseEntity.ok(placeService.getPlacesByKeyword(authToken, keyword));
    }

    /**
     * Get places by vicinity.
     * @param authToken the authentication token
     * @param dto the vicinity details
     * @return ResponseEntity with the places
     */
    @GetMapping(path = "/around", produces = "application/json")
    public @NotNull ResponseEntity<Iterable<Place>> getPlacesByVicinity(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @RequestBody @NotNull GetPlaceVicinityDTO dto) {
        System.out.println("PlaceController.getPlacesByVicinity: " + dto);
        return ResponseEntity.ok(placeService.getPlacesByVicinity(authToken, dto));
    }

    /**
     * Get place by id.
     * @param authToken the authentication token
     * @param id the place id
     * @return ResponseEntity with the place
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Place> getPlaceById(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("PlaceController.getPlaceById: " + id);
        return ResponseEntity.ok(placeService.getPlaceById(authToken, id));
    }

    /**
     * Get places by place tag id.
     * @param authToken the authentication token
     * @param id the place tag id
     * @return ResponseEntity with the places
     */
    @GetMapping(path = "/placeTag/{id}", produces = "application/json")
    public @NotNull ResponseEntity<Place> getPlacesByPlaceTag(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("PlaceController.getPlacesByPlaceTag: " + id);
        return ResponseEntity.ok(placeService.getPlacesByPlaceTag(authToken, id));
    }

    /**
     * Create a place.
     * @param authToken the authentication token
     * @param place the place to be created
     * @return ResponseEntity with the created place
     */
    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Place> createPlace(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @RequestBody @NotNull PlaceCreationDTO place) {
        System.out.println("PlaceController.createPlace: " + place);
        return ResponseEntity.ok(placeService.createPlace(authToken, place));
    }

    /**
     * Update a place.
     * @param authToken the authentication token
     * @param id the place id
     * @param place the place to be updated
     * @return ResponseEntity with the updated place
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public @NotNull ResponseEntity<Place> updatePlace(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id, @RequestBody @NotNull PlaceUpdateDTO place) {
        System.out.println("PlaceController.updatePlace: " + id + ": " + place);
        return ResponseEntity.ok(placeService.updatePlace(authToken, id, place));
    }

    /**
     * Delete a place.
     * @param authToken the authentication token
     * @param id the place id
     */
    @DeleteMapping(path = "/{id}")
    public @NotNull ResponseEntity<Void> deletePlace(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken, @PathVariable("id") Long id) {
        System.out.println("PlaceController.deletePlace: " + id);
        placeService.deletePlace(authToken, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Get real time places.
     * @param authToken the authentication token
     * @return ResponseEntity with the places
     */
    @GetMapping(path = "/realtime", produces = "application/json")
    public @NotNull ResponseEntity<Place> getRealTimePlaces(@CookieValue(AUTH_TOKEN_COOKIE_NAME) UUID authToken) {
        System.out.println("PlaceController.getRealTimePlaces: ");
        return ResponseEntity.ok(placeService.getRealtimePlace(authToken));
    }
}