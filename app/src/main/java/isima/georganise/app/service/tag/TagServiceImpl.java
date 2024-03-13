package isima.georganise.app.service.tag;


import isima.georganise.app.entity.dao.PlaceTag;
import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TagUpdateDTO;
import isima.georganise.app.entity.util.Right;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.exception.NotLoggedException;
import isima.georganise.app.exception.UnauthorizedException;
import isima.georganise.app.repository.PlacesRepository;
import isima.georganise.app.repository.TagsRepository;
import isima.georganise.app.repository.TokensRepository;
import isima.georganise.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TokensRepository tokensRepository;

    @Autowired
    PlacesRepository placesRepository;

    @Override
    public List<Tag> getAllTags(UUID authToken) {
        usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return tagsRepository.findAll();
    }

    @Override
    public Tag getTagById(UUID authToken, Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Tag tag = tagsRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!currentUser.getUserId().equals(tag.getUserId()))
            tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), id).orElseThrow(NotFoundException::new);

        return tag;
    }

    @Override
    public Iterable<Tag> getTagsByKeyword(UUID authToken, String keyword) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        return tagsRepository.findByKeywordAndUserId(keyword, currentUser.getUserId()).orElseThrow(NotFoundException::new);
    }

    @Override
    public Tag createTag(UUID authToken, TagCreationDTO tag) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        return tagsRepository.saveAndFlush(new Tag(tag, currentUser.getUserId()));
    }

    @Override
    public void deleteTag(UUID authToken, Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Tag tag = tagsRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!currentUser.getUserId().equals(tag.getUserId())) {
            List<Token> token = tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), id).orElseThrow(UnauthorizedException::new);
            if (token.stream().noneMatch(t -> t.getAccessRight().equals(Right.WRITER)))
                throw new UnauthorizedException(currentUser.getNickname(), "delete tag of user " + tag.getUserId());
        }

        tagsRepository.delete(tag);
    }

    @Override
    public Tag updateTag(UUID authToken, Long id, TagUpdateDTO tag) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Tag tagToUpdate = tagsRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!currentUser.getUserId().equals(tagToUpdate.getUserId())) {
            List<Token> token = tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), id).orElseThrow(UnauthorizedException::new);
            if (token.stream().noneMatch(t -> t.getAccessRight().equals(Right.WRITER)))
                throw new UnauthorizedException(currentUser.getNickname(), "update tag of user " + tagToUpdate.getUserId());
        }

        if (tag.getTitle() != null) {
            tagToUpdate.setTitle(tag.getTitle());
        }
        if (tag.getDescription() != null) {
            tagToUpdate.setDescription(tag.getDescription());
        }
        if (tag.getPlaceIds() != null) {
            List<PlaceTag> placeTag = new ArrayList<>();
            for (Long placeId : tag.getPlaceIds()) {
                placeTag.add(new PlaceTag(placesRepository.findById(placeId).orElseThrow(NotFoundException::new), tagToUpdate));
            }
            tagToUpdate.setPlaceTags(placeTag);
        }

        return tagsRepository.saveAndFlush(tagToUpdate);
    }
}

