package isima.georganise.app.service.place;

import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.entity.dto.GetPlaceVicinityDTO;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import isima.georganise.app.entity.dto.PlaceUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceService {

    public List<Place> getAllPlaces();

    public Place getPlaceById(Long id);

    public List<Place> getPlacesByUser(Long id);

    public List<Place> getPlacesByTag(Long id);

    public List<Place> getPlacesByKeyword(String keyword);

    public List<Place> getPlacesByVicinity(GetPlaceVicinityDTO dto);

    public Place createPlace(PlaceCreationDTO place);

    public void deletePlace(Long id);

    public Place updatePlace(Long id, PlaceUpdateDTO place);

}
