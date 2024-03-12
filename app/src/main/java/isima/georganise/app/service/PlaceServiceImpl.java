package isima.georganise.app.service;


import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.repository.PlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    PlacesRepository placesRepository;
    @Override
    public List<Place> places() {
        return placesRepository.findAll();
    }

    @Override
    public Place getPlaceById(Long id) {
        Optional<Place> op = placesRepository.findById(id);
        return op.orElseThrow(NotFoundException::new);
    }


    @Override
    public List<Place> getPlacesByUser(Long id) {
        Optional<List<Place>> op = placesRepository.findByUserId(id);
        if (op.isEmpty()) throw new NotFoundException();
        return op.get();
    }

    @Override
    public List<Place> getPlacesByTag(Long id) {
        Optional<List<Place>> op = placesRepository.findByTagId(id);
        if (op.isEmpty()) throw new NotFoundException();
        return op.get();
    }

    @Override
    public List<Place> getPlacesByKeyword(String keyword) {
        Optional<List<Place>> op = placesRepository.findByKeyword(keyword);
        if (op.isEmpty()) throw new NotFoundException();
        return op.get();
    }

    @Override
    public List<Place> getPlacesByVicinity() {
        Optional<List<Place>> op = placesRepository.findByVicinity();
        if (op.isEmpty()) throw new NotFoundException();
        return op.get();
    }

    @Override
    public Place addPlace(Place place) {
        return placesRepository.save(place);
    }

    @Override
    public void deletePlace(Long id) {
        Optional<Place> u = placesRepository.findById(id);
        if (u.isEmpty()) throw new NotFoundException();
        placesRepository.deleteById(id);
    }

    @Override
    public Place updatePlace(Long id, Place place) {
        Optional<Place> optionalPlace = placesRepository.findById(id);

        if (optionalPlace.isPresent()) {
            Place existingPlace = optionalPlace.get();
            if(place.getDescription() != null)
                existingPlace.setDescription(place.getDescription());
            if(place.getName() != null)
                existingPlace.setName(place.getName());
            if(place.getLatitude() != null)
                existingPlace.setLatitude(place.getLatitude());
            if(place.getLongitude() != null)
                existingPlace.setLongitude(place.getLongitude());


            return placesRepository.save(existingPlace);
        } else {
            throw new NotFoundException();
        }
    }
}

