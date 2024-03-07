package isima.georganise.app.service;

import isima.georganise.app.entity.dao.Place;

import java.util.List;

public interface PlaceService {
    public List<Place> places();
    public Place place(Long id);
    public Place addPlace(Place place);
    public boolean deletePlace(Long id);
    public Place updatePlace(Long id,Place place);
}
