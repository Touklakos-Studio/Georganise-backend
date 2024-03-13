package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.entity.dto.GetPlaceVicinityDTO;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import isima.georganise.app.entity.dto.PlaceUpdateDTO;
import isima.georganise.app.service.place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping(path="", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlaces(@CookieValue("authToken") UUID authToken) {
        return ResponseEntity.ok(placeService.getAllPlaces(authToken));
    }

    @GetMapping(path="/user/{id}", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlacesByUser(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getPlacesByUser(authToken, id));
    }

    @GetMapping(path="/tag/{id}", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlacesByTag(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getPlacesByTag(authToken, id));
    }

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlacesByKeyword(@CookieValue("authToken") UUID authToken, @PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(placeService.getPlacesByKeyword(authToken, keyword));
    }

    @GetMapping(path="/around", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlacesByVicinity(@CookieValue("authToken") UUID authToken, @RequestBody GetPlaceVicinityDTO dto) {
        return ResponseEntity.ok(placeService.getPlacesByVicinity(authToken, dto));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Place> getPlaceById(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getPlaceById(authToken, id));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Place> createPlace(@CookieValue("authToken") UUID authToken, @RequestBody PlaceCreationDTO place) {
        return ResponseEntity.ok(placeService.createPlace(authToken, place));
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Place> updatePlace(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id, @RequestBody PlaceUpdateDTO place) {
        return ResponseEntity.ok(placeService.updatePlace(authToken, id, place));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deletePlace(@CookieValue("authToken") UUID authToken, @PathVariable("id") Long id) {
        placeService.deletePlace(authToken, id);
        return ResponseEntity.ok().build();
    }
}
