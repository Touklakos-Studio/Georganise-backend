package isima.georganise.app.controller;

import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.exception.UnimplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @GetMapping(path="/user/{id}", produces = "application/json")
    public ResponseEntity<List<Place>> getPlacesByUser(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }

    @GetMapping(path="/tag/{id}", produces = "application/json")
    public ResponseEntity<List<Place>> getPlacesByTag(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }

    @GetMapping(path="/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<List<Place>> getPlacesByKeyword(@PathVariable("keyword") String keyword) {
        throw new UnimplementedException();
    }

    @GetMapping(path="/", produces = "application/json")
    public ResponseEntity<List<Place>> getPlacesByVicinity() {
        throw new UnimplementedException();
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Place> getPlaceById(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Place> createPlace(@RequestBody Place place) {
        throw new UnimplementedException();
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Place> updatePlace(@PathVariable("id") Long id, @RequestBody Place place) {
        throw new UnimplementedException();
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable("id") Long id) {
        throw new UnimplementedException();
    }
}
