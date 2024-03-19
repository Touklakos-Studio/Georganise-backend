package isima.georganise.app.service.place;


import isima.georganise.app.entity.dao.*;
import isima.georganise.app.entity.dto.GetPlaceVicinityDTO;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import isima.georganise.app.entity.dto.PlaceUpdateDTO;
import isima.georganise.app.exception.*;
import isima.georganise.app.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final @NotNull PlacesRepository placesRepository;

    private final @NotNull TokensRepository tokensRepository;

    private final @NotNull UsersRepository usersRepository;

    private final @NotNull TagsRepository tagsRepository;

    private final @NotNull PlacesTagsRepository placesTagsRepository;

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

    @Override
    public @NotNull List<Place> getAllPlaces(UUID authToken) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        List<Place> places = placesRepository.findAll();
        System.out.println("\tfetched " + places.size() + " places");
        places = removeUnauthorizedPlace(places, userCurrent);
        places.sort(Comparator.comparing(Place::getPlaceId));
        System.out.println("\treturning " + places.size() + " authorized places");

        return places;
    }

    @Override
    public @NotNull Place getPlaceById(UUID authToken, @NotNull Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        Place place = placesRepository.findById(id).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched place: " + place.getPlaceId());

        if (!userCurrent.getUserId().equals(place.getUserId())) {
            System.out.println("\tuser is not owner of place");
            List<Tag> tags = place.getPlaceTags().stream().map(PlaceTag::getTag).toList();
            System.out.println("\t\tfetched " + tags.size() + " tags");
            if (tags.isEmpty()) {
                System.out.println("\t\tplace has no tags");
                throw new NotFoundException("User " + userCurrent.getUserId() + " has no access to this place.");
            }
            tags.forEach(tag -> {
                List<Token> tokens = tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId());
                System.out.println("\t\t\tfetched " + tokens.size() + " tokens");
                if (tokens.isEmpty()) {
                    System.out.println("\t\t\tuser has no access token to tag " + tag.getTagId());
                    throw new NotFoundException("User " + userCurrent.getUserId() + " has no access token to this place.");
                }
                System.out.println("\t\t\tuser has access token: " + tokens.get(0).getTokenId());
            });
        }

        System.out.println("\treturning place: " + place.getPlaceId());
        return place;
    }


    @Override
    public @NotNull List<Place> getPlacesByUser(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());

        List<Place> places = placesRepository.findByUserId(id);
        System.out.println("\tfetched " + places.size() + " places");

        List<Place> placesToReturn = removeUnauthorizedPlace(places, userCurrent);
        placesToReturn.sort(Comparator.comparing(Place::getPlaceId));
        System.out.println("\treturning " + placesToReturn.size() + " authorized places");

        return placesToReturn;
    }

    @NotNull
    private List<Place> removeUnauthorizedPlace(List<Place> places, User userCurrent) {
        List<Place> placesToReturn = new ArrayList<>();
        for (Place place : places) {
            if (place.getUserId().equals(userCurrent.getUserId())) {
                placesToReturn.add(place);
                continue;
            }

            List<Tag> tags = place.getPlaceTags().stream().map(PlaceTag::getTag).toList();
            for (Tag tag : tags) {
                if (tag.getUserId().equals(userCurrent.getUserId()) || !tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId()).isEmpty()){
                    placesToReturn.add(place);
                    break;
                }
            }
        }
        return placesToReturn;
    }

    @Override
    public @NotNull List<Place> getPlacesByTag(UUID authToken, @NotNull Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        Tag tag = tagsRepository.findById(id).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched tag: " + tag.getTagId());

        if (!tag.getUserId().equals(userCurrent.getUserId())) {
            System.out.println("\t\tuser is not owner of tag: " + tag.getTagId());
            List<Token> token = tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId());
            System.out.println("\t\tfetched " + token.size() + " tokens");
            if (token.isEmpty()) {
                System.out.println("\t\t\tuser has no access token to tag: " + tag.getTagId());
                throw new NotFoundException("User " + userCurrent.getUserId() + " has no access token to tag " + tag.getTagId() + ".");
            }
            System.out.println("\t\t\tuser has access token: " + token.get(0).getTokenId());
        }

        List<Place> places = placesRepository.findByTagId(id);
        System.out.println("\tfetched " + places.size() + " places");

        if (places.isEmpty()) {
            System.out.println("\tuser has no places with tag: " + tag.getTagId());
            throw new NotFoundException("User " + userCurrent.getUserId() + " has no places with tag " + id + ".");
        }

        return places;
    }

    @Override
    public @NotNull List<Place> getPlacesByKeyword(UUID authToken, String keyword) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        List<Place> places = placesRepository.findByKeyword(keyword);
        System.out.println("\tfetched " + places.size() + " places");

        if (places.isEmpty()) {
            System.out.println("\tuser has no places with keyword: " + keyword);
            throw new NotFoundException("User " + userCurrent.getUserId() + " has no places with keyword " + keyword + ".");
        }

        List<Place> placesToReturn = removeUnauthorizedPlace(places, userCurrent);
        placesToReturn.sort(Comparator.comparing(Place::getPlaceId));
        System.out.println("\treturning " + placesToReturn.size() + " authorized places");

        return placesToReturn;
    }

    @Override
    public @NotNull List<Place> getPlacesByVicinity(UUID authToken, @NotNull GetPlaceVicinityDTO dto) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());

        BigDecimal minLongitude = dto.getLongitude().subtract(dto.getRadius());
        BigDecimal maxLongitude = dto.getLongitude().add(dto.getRadius());
        BigDecimal minLatitude = dto.getLatitude().subtract(dto.getRadius());
        BigDecimal maxLatitude = dto.getLatitude().add(dto.getRadius());
        System.out.println("\tsearching for places in vicinity: " + minLongitude + ", " + maxLongitude + ", " + minLatitude + ", " + maxLatitude);

        List<Place> places = placesRepository.findByVicinityAndUserId(minLongitude, maxLongitude, minLatitude, maxLatitude, userCurrent.getUserId()).orElse(new ArrayList<>());
        System.out.println("\tfetched " + places.size() + " places");

        if (places.isEmpty()) {
            System.out.println("\tuser has no places in vicinity");
            throw new NotFoundException("User " + userCurrent.getUserId() + " has no places in vicinity " + dto + ".");
        }

        return places;
    }

    @Override
    public @NotNull Place createPlace(UUID authToken, @NotNull PlaceCreationDTO placeCreationDTO) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());

        if (placeCreationDTO.isRealtime()) {
            Tag realtimeTag = tagsRepository.findByUserIdAndTitle(userCurrent.getUserId(), "{" + userCurrent.getNickname() + "} real time").orElseThrow(NotFoundException::new);
            List<Place> realtimePlace = placesRepository.findByTagId(realtimeTag.getTagId());
            if (!realtimePlace.isEmpty()) {
                System.out.println("\tuser already has a realtime place: " + realtimePlace.get(0).getPlaceId());
                throw new AlreadyCreatedException(realtimePlace.get(0).getPlaceId(), "User " + userCurrent.getUserId() + " already has a realtime place.");
            }
        }

        if (Objects.isNull(placeCreationDTO.getLatitude())) throw new IllegalArgumentException("Latitude is required");
        if (Objects.isNull(placeCreationDTO.getLongitude())) throw new IllegalArgumentException("Longitude is required");
        if (Objects.isNull(placeCreationDTO.getName())) throw new IllegalArgumentException("Name is required");
        if (Objects.isNull(placeCreationDTO.getTagIds())) placeCreationDTO.setTagIds((new ArrayList<>()));

        Place place = new Place(placeCreationDTO, userCurrent.getUserId());
        System.out.println("\tcreated place: " + place.getPlaceId());

        List<Tag> tags = tagsRepository.findAllById(placeCreationDTO.getTagIds());
        System.out.println("\tfetched " + tags.size() + " tags");
        List<PlaceTag> placeTags = new ArrayList<>();
        for (Tag tag : tags) {
            if (!tag.getUserId().equals(userCurrent.getUserId())) {
                System.out.println("\t\tuser is not owner of tag: " + tag.getTagId());
                List<Token> tokens = tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId());
                System.out.println("\t\tfetched " + tokens.size() + " tokens");
                if (tokens.isEmpty()) {
                    System.out.println("\t\t\tuser has no access token to tag: " + tag.getTagId());
                    throw new NotFoundException("User " + userCurrent.getUserId() + " has no access token to tag " + tag.getTagId() + ".");
                }
                System.out.println("\t\t\tuser has access token: " + tokens.get(0).getTokenId());
            }
            placeTags.add(new PlaceTag(place, tag));
            System.out.println("\t\tcreated place tag: " + placeTags.get(placeTags.size() - 1).getPlaceTagId());
        }
        placesRepository.saveAndFlush(place);
        System.out.println("\tsaved place: " + place.getPlaceId());

        place.setPlaceTags(placeTags);

        placesTagsRepository.saveAllAndFlush(placeTags);
        System.out.println("\tsaved place tags: " + placeTags.size());

        if (placeCreationDTO.isRealtime()) {
            Tag realtimeTag = tagsRepository.findByUserIdAndTitle(userCurrent.getUserId(), "{" + userCurrent.getNickname() + "} real time").orElseThrow(NotFoundException::new);
            System.out.println("\tfetched realtime tag: " + realtimeTag.getTagId());
            placesTagsRepository.save(new PlaceTag(place, realtimeTag));
            System.out.println("\tsaved realtime place tag");
        }

        return place;
    }

    @Override
    public void deletePlace(UUID authToken, @NotNull Long id) {
        Place place = checkPlaceAccessRights(authToken, id);

        place.getPlaceTags().forEach(placeTag -> System.out.println("\tdeleting place tag: " + placeTag.getPlaceTagId()));
        placesTagsRepository.deleteAll(place.getPlaceTags());
        System.out.println("\tdeleted place tags");

        placesRepository.delete(place);
        System.out.println("\tdeleted place: " + place.getPlaceId());
    }

    @Override
    public @NotNull Place updatePlace(UUID authToken, @NotNull Long id, @NotNull PlaceUpdateDTO place) {
        Place existingPlace = checkPlaceAccessRights(authToken, id);

        if (place.getName() != null) {
            System.out.println("\tupdating name to: " + place.getName() + " from: " + existingPlace.getName());
            existingPlace.setName(place.getName());
        }
        if (place.getDescription() != null) {
            System.out.println("\tupdating description to: " + place.getDescription() + " from: " + existingPlace.getDescription());
            existingPlace.setDescription(place.getDescription());
        }
        if (place.getLongitude() != null) {
            System.out.println("\tupdating longitude to: " + place.getLongitude() + " from: " + existingPlace.getLongitude());
            existingPlace.setLongitude(place.getLongitude());
        }
        if (place.getLatitude() != null) {
            System.out.println("\tupdating latitude to: " + place.getLatitude() + " from: " + existingPlace.getLatitude());
            existingPlace.setLatitude(place.getLatitude());
        }
        if (place.getImageId() != null) {
            System.out.println("\tupdating imageId to: " + place.getImageId() + " from: " + existingPlace.getImageId());
            existingPlace.setImageId(place.getImageId());
        }
        List<PlaceTag> placeTags = new ArrayList<>();
        for (Long tagId : place.getTagIds()) {
            System.out.println("\tupdating tagId: " + tagId);
            Tag tag = tagsRepository.findById(tagId).orElseThrow(NotFoundException::new);
            System.out.println("\tfetched tag: " + tag.getTagId());
            if (!tag.getUserId().equals(existingPlace.getUserId())) {
                System.out.println("\t\tuser is not owner of tag: " + tag.getTagId());
                List<Token> tokens = tokensRepository.findByUserIdAndTagId(existingPlace.getUserId(), tag.getTagId());
                System.out.println("\t\tfetched " + tokens.size() + " tokens");
                if (tokens.isEmpty()) {
                    System.out.println("\t\t\tuser has no access token to tag: " + tag.getTagId());
                    throw new NotFoundException("User " + existingPlace.getUserId() + " has no access token to tag " + tag.getTagId() + ".");
                }
                System.out.println("\t\t\tuser has access token: " + tokens.get(0).getTokenId());
            }
            placeTags.add(new PlaceTag(existingPlace, tag));
            System.out.println("\t\tcreated place tag: " + placeTags.get(placeTags.size() - 1).getPlaceTagId());
        }
        placesTagsRepository.deleteAll(existingPlace.getPlaceTags());
        System.out.println("\tdeleted place tags: " + existingPlace.getPlaceTags().size());
        placesTagsRepository.saveAllAndFlush(placeTags);
        System.out.println("\tsaved place tags: " + placeTags.size());
        existingPlace.setPlaceTags(placeTags);

        Place existingPlaceUpdated = placesRepository.saveAndFlush(existingPlace);
        System.out.println("\tupdated place: " + existingPlaceUpdated);

        return existingPlaceUpdated;
    }

    @Override
    public Place getPlacesByPlaceTag(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        PlaceTag placeTag = placesTagsRepository.findById(id).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched place tag: " + placeTag.getPlaceTagId());

        if (!placeTag.getPlace().getUserId().equals(userCurrent.getUserId())) {
            System.out.println("\tuser is not owner of place: " + placeTag.getPlace().getPlaceId());
            List<Token> tokens = tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), placeTag.getTag().getTagId());
            System.out.println("\tfetched " + tokens.size() + " tokens");
            if (tokens.isEmpty()) {
                System.out.println("\t\tuser has no access token to tag: " + placeTag.getTag().getTagId());
                throw new UnauthorizedException(userCurrent.getNickname(), "get place of user " + placeTag.getPlace().getUserId());
            }
            System.out.println("\t\tuser has access token: " + tokens.get(0).getTokenId());
        }

        return placeTag.getPlace();
    }

    @NotNull
    private Place checkPlaceAccessRights(UUID authToken, @NotNull Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        Place existingPlace = placesRepository.findById(id).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched place: " + existingPlace.getPlaceId());

        if (!userCurrent.getUserId().equals(existingPlace.getUserId())) {
            System.out.println("\tuser is not owner of place: " + existingPlace.getPlaceId());
            throw new NotFoundException();
        }

        System.out.println("\tuser is owner of place: " + existingPlace.getPlaceId());
        return existingPlace;
    }
}

