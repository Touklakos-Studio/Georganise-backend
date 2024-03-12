package isima.georganise.app.service;

import isima.georganise.app.entity.dao.Place;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceService {
    public List<Place> places();
    public Place getPlaceById(Long id);
    public List<Place> getPlacesByUser(Long id);
    public List<Place> getPlacesByTag(Long id);
    public List<Place> getPlacesByKeyword(String keyword);
    public List<Place> getPlacesByVicinity();
    public Place addPlace(Place place);
    public void deletePlace(Long id);
    public Place updatePlace(Long id,Place place);
}
