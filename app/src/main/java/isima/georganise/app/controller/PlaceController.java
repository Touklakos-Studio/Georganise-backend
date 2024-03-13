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

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping(path="", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlaces() {
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

    @GetMapping(path="/user/{id}", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlacesByUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getPlacesByUser(id));
    }

    @GetMapping(path="/tag/{id}", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlacesByTag(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getPlacesByTag(id));
    }

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlacesByKeyword(@PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(placeService.getPlacesByKeyword(keyword));
    }

    @GetMapping(path="/around", produces = "application/json")
    public ResponseEntity<Iterable<Place>> getPlacesByVicinity(@RequestBody GetPlaceVicinityDTO dto) {
        return ResponseEntity.ok(placeService.getPlacesByVicinity(dto));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Place> getPlaceById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getPlaceById(id));
    }

    @PostMapping(path="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Place> createPlace(@RequestBody PlaceCreationDTO place) {
        return ResponseEntity.ok(placeService.createPlace(place));
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Place> updatePlace(@PathVariable("id") Long id, @RequestBody PlaceUpdateDTO place) {
        return ResponseEntity.ok(placeService.updatePlace(id, place));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable("id") Long id) {
        placeService.deletePlace(id);
        return ResponseEntity.ok().build();
    }
}
