package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Place;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @GetMapping(path="/user/{id}", produces = "application/json")
    public ResponseEntity<List<Place>> getPlacesByUser(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping(path="/tag/{id}", produces = "application/json")
    public ResponseEntity<List<Place>> getPlacesByTag(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<List<Place>> getPlacesByKeyword(@PathVariable("keyword") String keyword) {
        return null;
    }

    @GetMapping(path="/", produces = "application/json")
    public ResponseEntity<List<Place>> getPlacesByVicinity() {
        return null;
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Place> getPlaceById(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Place> createPlace(@RequestBody Place place) {
        return null;
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Place> updatePlace(@PathVariable("id") Long id, @RequestBody Place place) {
        return null;
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable("id") Long id) {
        return null;
    }
}
