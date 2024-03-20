package isima.georganise.app.service.place;

import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.entity.dto.GetPlaceVicinityDTO;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import isima.georganise.app.entity.dto.PlaceUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * This interface represents the PlaceService.
 * It provides methods to interact with the Place entity.
 */
@Service
public interface PlaceService {

    /**
     * This method retrieves all places for a given user.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @return A List of Place.
     */
    List<Place> getAllPlaces(UUID authToken);

    /**
     * This method retrieves a place by its id for a given user.
     * It returns a Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place.
     * @return A Place.
     */
    Place getPlaceById(UUID authToken, Long id);

    /**
     * This method retrieves places by user id.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the user.
     * @return A List of Place.
     */
    List<Place> getPlacesByUser(UUID authToken, Long id);

    /**
     * This method retrieves places by tag id.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     * @return A List of Place.
     */
    List<Place> getPlacesByTag(UUID authToken, Long id);

    /**
     * This method retrieves places by keyword.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @param keyword The keyword to search for in the places.
     * @return A List of Place.
     */
    List<Place> getPlacesByKeyword(UUID authToken, String keyword);

    /**
     * This method retrieves places by vicinity for a given user.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @param dto The GetPlaceVicinityDTO containing the data to get the places by vicinity.
     * @return A List of Place.
     */
    List<Place> getPlacesByVicinity(UUID authToken, GetPlaceVicinityDTO dto);

    /**
     * This method creates a place for a given user.
     * It returns a Place.
     *
     * @param authToken The authentication token of the user.
     * @param place The PlaceCreationDTO containing the data to create the place.
     * @return A Place.
     */
    Place createPlace(UUID authToken, PlaceCreationDTO place);

    /**
     * This method deletes a place by its id for a given user.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place.
     */
    void deletePlace(UUID authToken, Long id);

    /**
     * This method updates a place by its id for a given user.
     * It returns a Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place.
     * @param place The PlaceUpdateDTO containing the data to update the place.
     * @return A Place.
     */
    Place updatePlace(UUID authToken, Long id, PlaceUpdateDTO place);

    /**
     * This method retrieves a place by place tag for a given user.
     * It returns a Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place tag.
     * @return A Place.
     */
    Place getPlacesByPlaceTag(UUID authToken, Long id);

    /**
     * This method retrieves a real-time place for a given user.
     * It returns a Place.
     *
     * @param authToken The authentication token of the user.
     * @return A Place.
     */
    Place getRealtimePlace(UUID authToken);
}