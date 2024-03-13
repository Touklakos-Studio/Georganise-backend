package isima.georganise.app.service.place;


import isima.georganise.app.entity.dao.*;
import isima.georganise.app.entity.dto.GetPlaceVicinityDTO;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import isima.georganise.app.entity.dto.PlaceUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.exception.NotLoggedException;
import isima.georganise.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    PlacesRepository placesRepository;

    @Autowired
    TokensRepository tokensRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TagsRepository tagsRepository;

    @Override
    public List<Place> getAllPlaces(UUID authToken) {
        usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return placesRepository.findAll();
    }

    @Override
    public Place getPlaceById(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Place place = placesRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!userCurrent.getUserId().equals(place.getUserId())) {
            place.getPlaceTags().forEach(placeTag -> tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), placeTag.getTag().getTagId()).orElseThrow(NotFoundException::new));
        }

        return place;
    }


    @Override
    public List<Place> getPlacesByUser(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (!userCurrent.getUserId().equals(id))
            throw new NotFoundException();

        return placesRepository.findByUserId(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Place> getPlacesByTag(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return placesRepository.findByTagIdAndUserId(id, userCurrent.getUserId()).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Place> getPlacesByKeyword(UUID authToken, String keyword) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return placesRepository.findByKeywordAndUserId(keyword, userCurrent.getUserId()).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Place> getPlacesByVicinity(UUID authToken, GetPlaceVicinityDTO dto) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        BigDecimal minLongitude = dto.getLongitude().subtract(dto.getRadius());
        BigDecimal maxLongitude = dto.getLongitude().add(dto.getRadius());
        BigDecimal minLatitude = dto.getLatitude().subtract(dto.getRadius());
        BigDecimal maxLatitude = dto.getLatitude().add(dto.getRadius());

        return placesRepository.findByVicinityAndUserId(minLongitude, maxLongitude, minLatitude, maxLatitude, userCurrent.getUserId()).orElseThrow(NotFoundException::new);
    }

    @Override
    public Place createPlace(UUID authToken, PlaceCreationDTO placeCreationDTO) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return placesRepository.saveAndFlush(new Place(placeCreationDTO, userCurrent.getUserId()));
    }

    @Override
    public void deletePlace(UUID authToken, Long id) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Place place = placesRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!userCurrent.getUserId().equals(place.getUserId()))
            throw new NotFoundException();

        placesRepository.delete(place);
    }

    @Override
    public Place updatePlace(UUID authToken, Long id, PlaceUpdateDTO place) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Place existingPlace = placesRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!userCurrent.getUserId().equals(existingPlace.getUserId()))
            throw new NotFoundException();

        if(place.getName() != null)
            existingPlace.setName(place.getName());
        if(place.getDescription() != null)
            existingPlace.setDescription(place.getDescription());
        if(place.getLongitude() != null)
            existingPlace.setLongitude(place.getLongitude());
        if(place.getLatitude() != null)
            existingPlace.setLatitude(place.getLatitude());
        if(place.getImageId() != null)
            existingPlace.setImageId(place.getImageId());
        if(place.getTagIds() != null) {
            for (Long tagId : place.getTagIds()) {
                if (existingPlace.getPlaceTags().stream().noneMatch(placeTag -> placeTag.getTag().getTagId().equals(tagId))) {
                    Tag tag = tagsRepository.findById(tagId).orElseThrow(NotFoundException::new);
                    existingPlace.getPlaceTags().add(new PlaceTag(existingPlace, tag));
                }
            }
        }

        return placesRepository.saveAndFlush(existingPlace);
    }
}

