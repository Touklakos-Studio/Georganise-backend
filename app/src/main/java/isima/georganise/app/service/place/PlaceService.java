package isima.georganise.app.service.place;

import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.entity.dto.GetPlaceVicinityDTO;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import isima.georganise.app.entity.dto.PlaceUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PlaceService {

    public List<Place> getAllPlaces(UUID authToken);

    public Place getPlaceById(UUID authToken, Long id);

    public List<Place> getPlacesByUser(UUID authToken, Long id);

    public List<Place> getPlacesByTag(UUID authToken, Long id);

    public List<Place> getPlacesByKeyword(UUID authToken, String keyword);

    public List<Place> getPlacesByVicinity(UUID authToken, GetPlaceVicinityDTO dto);

    public Place createPlace(UUID authToken, PlaceCreationDTO place);

    public void deletePlace(UUID authToken, Long id);

    public Place updatePlace(UUID authToken, Long id, PlaceUpdateDTO place);

}
