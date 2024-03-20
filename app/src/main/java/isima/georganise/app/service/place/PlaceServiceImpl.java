package isima.georganise.app.service.place;

import isima.georganise.app.entity.dao.*;
import isima.georganise.app.entity.dto.GetPlaceVicinityDTO;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import isima.georganise.app.entity.dto.PlaceUpdateDTO;
import isima.georganise.app.entity.util.Right;
import isima.georganise.app.exception.AlreadyCreatedException;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.exception.NotLoggedException;
import isima.georganise.app.exception.UnauthorizedException;
import isima.georganise.app.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

/**
 * This class implements the PlaceService interface.
 * It provides methods to interact with the Place entity.
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    private final @NotNull PlacesRepository placesRepository;
    private final @NotNull TokensRepository tokensRepository;
    private final @NotNull UsersRepository usersRepository;
    private final @NotNull TagsRepository tagsRepository;
    private final @NotNull PlacesTagsRepository placesTagsRepository;

    /**
     * This constructor initializes the PlaceServiceImpl with the necessary repositories.
     *
     * @param tagsRepository The repository to interact with the Tag entity.
     * @param placesRepository The repository to interact with the Place entity.
     * @param tokensRepository The repository to interact with the Token entity.
     * @param usersRepository The repository to interact with the User entity.
     * @param placesTagsRepository The repository to interact with the PlaceTag entity.
     */
    @Autowired
    public PlaceServiceImpl(@NotNull TagsRepository tagsRepository, @NotNull PlacesRepository placesRepository, @NotNull TokensRepository tokensRepository, @NotNull UsersRepository usersRepository, @NotNull PlacesTagsRepository placesTagsRepository) {
        Assert.notNull(tagsRepository, "TagsRepository cannot be null");
        Assert.notNull(placesRepository, "PlacesRepository cannot be null");
        Assert.notNull(tokensRepository, "TokensRepository cannot be null");
        Assert.notNull(usersRepository, "UsersRepository cannot be null");
        Assert.notNull(placesTagsRepository, "PlacesTagsRepository cannot be null");
        this.tagsRepository = tagsRepository;
        this.placesRepository = placesRepository;
        this.tokensRepository = tokensRepository;
        this.usersRepository = usersRepository;
        this.placesTagsRepository = placesTagsRepository;
    }

    /**
     * This method retrieves all places for a given user.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @return A List of Place.
     */
    @Override
    public @NotNull List<Place> getAllPlaces(UUID authToken) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        List<Place> places = placesRepository.findAll();
        places = removeUnauthorizedPlace(places, userCurrent);
        places.sort(Comparator.comparing(Place::getPlaceId));

        return places;
    }

    /**
     * This method retrieves a place by its id for a given user.
     * It returns a Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place.
     * @return A Place.
     */
    @Override
    public @NotNull Place getPlaceById(UUID authToken, @NotNull Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Place place = placesRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!userCurrent.getUserId().equals(place.getUserId())) {
            List<Tag> tags = place.getPlaceTags().stream().map(PlaceTag::getTag).toList();
            if (tags.isEmpty()) throw new NotFoundException("User " + userCurrent.getUserId() + " has no access to this place.");
            tags.forEach(tag -> {
                List<Token> tokens = tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId());
                if (tokens.isEmpty()) throw new NotFoundException("User " + userCurrent.getUserId() + " has no access token to this place.");
            });
        }

        return place;
    }

    /**
     * This method retrieves places by user id.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the user.
     * @return A List of Place.
     */
    @Override
    public @NotNull List<Place> getPlacesByUser(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        List<Place> placesToReturn = removeUnauthorizedPlace(placesRepository.findByUserId(id), userCurrent);
        placesToReturn.sort(Comparator.comparing(Place::getPlaceId));

        return placesToReturn;
    }

    /**
     * This method removes unauthorized places from a list of places.
     * It returns a List of Place.
     *
     * @param places The list of places to check.
     * @param userCurrent The current user.
     * @return A List of Place.
     */
    @NotNull
    private List<Place> removeUnauthorizedPlace(@NotNull List<Place> places, @NotNull User userCurrent) {
        List<Place> placesToReturn = new ArrayList<>();
        for (Place place : places) {
            if (place.getUserId().equals(userCurrent.getUserId())) {
                placesToReturn.add(place);
                continue;
            }

            List<Tag> tags = place.getPlaceTags().stream().map(PlaceTag::getTag).toList();
            for (Tag tag : tags) {
                if (tag.getUserId().equals(userCurrent.getUserId()) || !tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId()).isEmpty()) {
                    placesToReturn.add(place);
                    break;
                }
            }
        }
        return placesToReturn;
    }

    /**
     * This method retrieves places by tag id.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     * @return A List of Place.
     */
    @Override
    public @NotNull List<Place> getPlacesByTag(UUID authToken, @NotNull Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Tag tag = tagsRepository.findById(id).orElseThrow(NotFoundException::new);

        if (tag.getTitle().contains("} real time")) throw new NotFoundException("Tag " + tag.getTagId() + " is real time.");

        if (!tag.getUserId().equals(userCurrent.getUserId()) && (tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId()).isEmpty()))
            throw new NotFoundException("User " + userCurrent.getUserId() + " has no access token to tag " + tag.getTagId() + ".");

        List<Place> places = placesRepository.findByTagId(id);

        if (places.isEmpty()) throw new NotFoundException("User " + userCurrent.getUserId() + " has no places with tag " + id + ".");

        return places;
    }

    /**
     * This method retrieves places by keyword.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @param keyword The keyword to search for in the places.
     * @return A List of Place.
     */
    @Override
    public @NotNull List<Place> getPlacesByKeyword(UUID authToken, String keyword) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        List<Place> places = placesRepository.findByKeyword(keyword);

        if (places.isEmpty()) throw new NotFoundException("User " + userCurrent.getUserId() + " has no places with keyword " + keyword + ".");

        List<Place> placesToReturn = removeUnauthorizedPlace(places, userCurrent);
        placesToReturn.sort(Comparator.comparing(Place::getPlaceId));

        return placesToReturn;
    }

    /**
     * This method retrieves places by vicinity for a given user.
     * It returns a List of Place.
     *
     * @param authToken The authentication token of the user.
     * @param dto The GetPlaceVicinityDTO containing the data to get the places by vicinity.
     * @return A List of Place.
     */
    @Override
    public @NotNull List<Place> getPlacesByVicinity(UUID authToken, @NotNull GetPlaceVicinityDTO dto) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        BigDecimal minLongitude = dto.getLongitude().subtract(dto.getRadius());
        BigDecimal maxLongitude = dto.getLongitude().add(dto.getRadius());
        BigDecimal minLatitude = dto.getLatitude().subtract(dto.getRadius());
        BigDecimal maxLatitude = dto.getLatitude().add(dto.getRadius());

        List<Place> places = placesRepository.findByVicinityAndUserId(minLongitude, maxLongitude, minLatitude, maxLatitude, userCurrent.getUserId()).orElse(new ArrayList<>());

        if (places.isEmpty()) {
            throw new NotFoundException("User " + userCurrent.getUserId() + " has no places in vicinity " + dto + ".");
        }

        places = removeUnauthorizedPlace(places, userCurrent);

        return places;
    }

    /**
     * This method creates a place for a given user.
     * It returns a Place.
     *
     * @param authToken The authentication token of the user.
     * @param placeCreationDTO The PlaceCreationDTO containing the data to create the place.
     * @return A Place.
     */
    @Override
    public @NotNull Place createPlace(UUID authToken, @NotNull PlaceCreationDTO placeCreationDTO) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (placeCreationDTO.isRealtime()) {
            List<Place> realtimePlace = placesRepository.findByTagId(tagsRepository.findByUserIdAndTitle(userCurrent.getUserId(), "{" + userCurrent.getNickname() + "} real time").orElseThrow(NotFoundException::new).getTagId());
            if (!realtimePlace.isEmpty()) throw new AlreadyCreatedException(realtimePlace.get(0).getPlaceId(), "User " + userCurrent.getUserId() + " already has a realtime place.");
        }

        if (Objects.isNull(placeCreationDTO.getLatitude())) throw new IllegalArgumentException("Latitude is required");
        if (Objects.isNull(placeCreationDTO.getLongitude())) throw new IllegalArgumentException("Longitude is required");
        if (Objects.isNull(placeCreationDTO.getName())) throw new IllegalArgumentException("Name is required");
        if (Objects.isNull(placeCreationDTO.getTagIds())) placeCreationDTO.setTagIds((new ArrayList<>()));

        Place place = new Place(placeCreationDTO, userCurrent.getUserId());

        List<PlaceTag> placeTags = new ArrayList<>();
        for (Tag tag : tagsRepository.findAllById(placeCreationDTO.getTagIds())) {
            if (tag.getTitle().contains("} real time")) throw new NotFoundException("Tag " + tag.getTagId() + " is real time.");
            if (!tag.getUserId().equals(userCurrent.getUserId())) {
                List<Token> tokens = tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId());
                if (tokens.isEmpty()) throw new NotFoundException("User " + userCurrent.getUserId() + " has no access token to tag " + tag.getTagId() + ".");
                if (tokens.stream().noneMatch(token -> token.getAccessRight().equals(Right.WRITER))) throw new UnauthorizedException(userCurrent.getNickname(), "create place with tag " + tag.getTagId());
            }
            placeTags.add(new PlaceTag(place, tag));
        }
        placesRepository.saveAndFlush(place);

        place.setPlaceTags(placeTags);

        placesTagsRepository.saveAllAndFlush(placeTags);

        if (placeCreationDTO.isRealtime()) placesTagsRepository.save(new PlaceTag(place, tagsRepository.findByUserIdAndTitle(userCurrent.getUserId(), "{" + userCurrent.getNickname() + "} real time").orElseThrow(NotFoundException::new)));

        return place;
    }

    /**
     * This method deletes a place by its id for a given user.
     * It first checks if the user has access rights to the place.
     * If the user has access rights, it deletes all the place tags associated with the place.
     * Finally, it deletes the place from the repository.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place.
     */
    @Override
    public void deletePlace(UUID authToken, @NotNull Long id) {
        Place place = checkPlaceAccessRights(authToken, id);

        placesTagsRepository.deleteAll(place.getPlaceTags());

        placesRepository.delete(place);
    }

    /**
     * This method updates a place by its id for a given user.
     * It first checks if the user has access rights to the place.
     * If the user has access rights, it updates the place's details based on the provided PlaceUpdateDTO.
     * If the PlaceUpdateDTO contains a list of tag ids, it updates the place's tags accordingly.
     * It then saves the updated place in the repository.
     * It returns the updated Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place to be updated.
     * @param place The PlaceUpdateDTO containing the data to update the place.
     * @return The updated Place.
     * @throws NotFoundException if the place or tag is not found.
     * @throws UnauthorizedException if the user does not have access rights to the place or tag.
     */
    @Override
    public @NotNull Place updatePlace(UUID authToken, @NotNull Long id, @NotNull PlaceUpdateDTO place) {
        Place existingPlace = checkPlaceAccessRights(authToken, id);

        if (place.getName() != null) existingPlace.setName(place.getName());
        if (place.getDescription() != null) existingPlace.setDescription(place.getDescription());
        if (place.getLongitude() != null) existingPlace.setLongitude(place.getLongitude());
        if (place.getLatitude() != null) existingPlace.setLatitude(place.getLatitude());
        if (place.getImageId() != null) existingPlace.setImageId(place.getImageId());
        if (place.getTagIds() != null) {
            List<PlaceTag> placeTags = new ArrayList<>();
            for (Long tagId : place.getTagIds()) {
                Tag tag = tagsRepository.findById(tagId).orElseThrow(NotFoundException::new);
                if (!tag.getUserId().equals(existingPlace.getUserId())) {
                    List<Token> tokens = tokensRepository.findByUserIdAndTagId(existingPlace.getUserId(), tag.getTagId());
                    if (tokens.isEmpty()) throw new NotFoundException("User " + existingPlace.getUserId() + " has no access token to tag " + tag.getTagId() + ".");
                    if (tokens.stream().noneMatch(token -> token.getAccessRight().equals(Right.WRITER))) throw new UnauthorizedException(existingPlace.getUserId().toString(), "update place with tag " + tag.getTagId());
                }
                placeTags.add(new PlaceTag(existingPlace, tag));
            }
            placesTagsRepository.deleteAll(existingPlace.getPlaceTags());
            placesTagsRepository.saveAllAndFlush(placeTags);
            existingPlace.setPlaceTags(placeTags);
        }

        return placesRepository.saveAndFlush(existingPlace);
    }

    /**
     * This method retrieves places by tag id for a given user.
     * It returns a Place.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     * @return A Place.
     */
    @Override
    public Place getPlacesByPlaceTag(UUID authToken, @NotNull Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        PlaceTag placeTag = placesTagsRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!placeTag.getPlace().getUserId().equals(userCurrent.getUserId()) && (tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), placeTag.getTag().getTagId()).isEmpty()))
            throw new UnauthorizedException(userCurrent.getNickname(), "get place of user " + placeTag.getPlace().getUserId());

        return placeTag.getPlace();
    }

    /**
     * This method checks if the user has access rights to a place.
     * It returns the place if the user has access rights.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place.
     * @return The place if the user has access rights.
     * @throws NotFoundException if the place is not found.
     * @throws UnauthorizedException if the user does not have access rights to the place.
     */
    @NotNull
    private Place checkPlaceAccessRights(UUID authToken, @NotNull Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Place existingPlace = placesRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!userCurrent.getUserId().equals(existingPlace.getUserId())) throw new UnauthorizedException(userCurrent.getNickname(), "update place " + id);

        return existingPlace;
    }

    /**
     * This method retrieves the realtime place for a given user.
     * It returns a Place.
     *
     * @param authToken The authentication token of the user.
     * @return A Place.
     */
    @Override
    public Place getRealtimePlace(UUID authToken) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        List<Place> realtimePlace = placesRepository.findByTagId(tagsRepository.findByUserIdAndTitle(userCurrent.getUserId(), "{" + userCurrent.getNickname() + "} real time").orElseThrow(NotFoundException::new).getTagId());

        if (realtimePlace.isEmpty()) throw new NotFoundException("User " + userCurrent.getUserId() + " has no realtime place.");

        return realtimePlace.get(0);
    }
}

