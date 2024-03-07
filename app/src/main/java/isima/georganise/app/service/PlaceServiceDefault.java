package isima.georganise.app.service;


import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.repository.PlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceDefault implements PlaceService{

    @Autowired
    PlacesRepository placesRepository;
    @Override
    public List<Place> places() {
        return placesRepository.findAll();
    }

    @Override
    public Place place(Long id) {
        Optional<Place> op = placesRepository.findById(id);
        if(op.isPresent()) {
            return op.get();
        }
        else {
            return null;
        }
    }

    @Override
    public Place addPlace(Place place) {
        return placesRepository.save(place);
    }

    @Override
    public boolean deletePlace(Long id) {
        Optional<Place> u = placesRepository.findById(id);
        if (u.isPresent()) {
            placesRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
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
            return null;
        }
    }
}

