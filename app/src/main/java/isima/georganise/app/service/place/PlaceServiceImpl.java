package isima.georganise.app.service.place;


import isima.georganise.app.entity.dao.Place;
import isima.georganise.app.entity.dao.PlaceTag;
import isima.georganise.app.entity.dto.GetPlaceVicinityDTO;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import isima.georganise.app.entity.dto.PlaceUpdateDTO;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.repository.PlacesRepository;
import isima.georganise.app.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    PlacesRepository placesRepository;

    @Autowired
    TagsRepository tagsRepository;

    @Override
    public List<Place> getAllPlaces() {
        return placesRepository.findAll();
    }

    @Override
    public Place getPlaceById(Long id) {
        return placesRepository.findById(id).orElseThrow(NotFoundException::new);
    }


    @Override
    public List<Place> getPlacesByUser(Long id) {
        return placesRepository.findByUserId(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Place> getPlacesByTag(Long id) {
        return placesRepository.findByTagId(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Place> getPlacesByKeyword(String keyword) {
        return placesRepository.findByKeyword(keyword).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Place> getPlacesByVicinity(GetPlaceVicinityDTO dto) {
        BigDecimal minLongitude = dto.getLongitude().subtract(dto.getRadius());
        BigDecimal maxLongitude = dto.getLongitude().add(dto.getRadius());
        BigDecimal minLatitude = dto.getLatitude().subtract(dto.getRadius());
        BigDecimal maxLatitude = dto.getLatitude().add(dto.getRadius());
        return placesRepository.findByVicinity(minLongitude, maxLongitude, minLatitude, maxLatitude).orElseThrow(NotFoundException::new);
    }

    @Override
    public Place createPlace(PlaceCreationDTO placeCreationDTO) {
        return placesRepository.saveAndFlush(new Place(placeCreationDTO, 1L));
    }

    @Override
    public void deletePlace(Long id) {
        placesRepository.delete(placesRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Place updatePlace(Long id, PlaceUpdateDTO place) {
        Place existingPlace = placesRepository.findById(id).orElseThrow(NotFoundException::new);

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
        //TODO: update tags

        return placesRepository.saveAndFlush(existingPlace);
    }
}

