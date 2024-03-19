package isima.georganise.app.service.tag;


import isima.georganise.app.entity.dao.PlaceTag;
import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dao.Token;
import isima.georganise.app.entity.dao.User;
import isima.georganise.app.entity.dto.RemovePlaceFromTagDTO;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TagUpdateDTO;
import isima.georganise.app.entity.util.Right;
import isima.georganise.app.exception.ConflictException;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.exception.NotLoggedException;
import isima.georganise.app.exception.UnauthorizedException;
import isima.georganise.app.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class TagServiceImpl implements TagService {

    private final @NotNull TagsRepository tagsRepository;

    private final @NotNull UsersRepository usersRepository;

    private final @NotNull TokensRepository tokensRepository;

    private final @NotNull PlacesRepository placesRepository;

    private final @NotNull PlacesTagsRepository placesTagsRepository;

    @Autowired
    public TagServiceImpl(@NotNull PlacesRepository placesRepository, @NotNull TagsRepository tagsRepository, @NotNull UsersRepository usersRepository, @NotNull TokensRepository tokensRepository, @NotNull PlacesTagsRepository placesTagsRepository) {
        Assert.notNull(placesRepository, "placesRepository must not be null");
        Assert.notNull(tagsRepository, "tagsRepository must not be null");
        Assert.notNull(usersRepository, "usersRepository must not be null");
        Assert.notNull(tokensRepository, "tokensRepository must not be null");
        Assert.notNull(placesTagsRepository, "placesTagsRepository must not be null");
        this.placesRepository = placesRepository;
        this.tagsRepository = tagsRepository;
        this.usersRepository = usersRepository;
        this.tokensRepository = tokensRepository;
        this.placesTagsRepository = placesTagsRepository;
    }

    @Override
    public @NotNull List<Tag> getAllTags(UUID authToken) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + userCurrent.getUserId());
        List<Tag> tags = tagsRepository.findAll();
        System.out.println("\tfetched " + tags.size() + " tags");

        if (tags.isEmpty()) {
            System.out.println("\tNo tags found");
            throw new NotFoundException();
        }

        List<Tag> tagsToReturn = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getUserId().equals(userCurrent.getUserId())) {
                tagsToReturn.add(tag);
            } else {
                List<Token> tokens = tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId());
                if (!tokens.isEmpty())
                    tagsToReturn.add(tag);
            }
        }
        System.out.println("\treturning " + tagsToReturn.size() + " authorized tags");

        tagsToReturn = tagsToReturn.stream().filter(tag -> !tag.getTitle().contains("} real time")).toList();

        System.out.println("\treturning " + tagsToReturn.size() + " non realtime tags");
        return tagsToReturn;
    }

    @Override
    public @NotNull Tag getTagById(UUID authToken, @NotNull Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());
        Tag tag = tagsRepository.findById(id).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched tag: " + tag.getTagId());

        if (!currentUser.getUserId().equals(tag.getUserId())) {
            System.out.println("\t\tuser " + currentUser.getUserId() + " is not the owner of the tag " + tag.getTagId() + " user " + tag.getUserId() + " is");
            List<Token> tokens = tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), tag.getTagId());
            System.out.println("\t\t\tfetched " + tokens.size() + " tokens");
            if (tokens.isEmpty()) {
                System.out.println("\t\t\tuser " + currentUser.getUserId() + " has no token for tag " + tag.getTagId());
                throw new UnauthorizedException(currentUser.getNickname(), "get tag of user " + tag.getUserId());
            }
            System.out.println("\t\t\tuser has access token: " + tokens.get(0).getTokenId());
        }

        return tag;
    }

    @Override
    public Iterable<Tag> getTagsByKeyword(UUID authToken, String keyword) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());
        List<Tag> tags = tagsRepository.findByKeyword(keyword);
        System.out.println("\tfetched " + tags.size() + " tags");

        if (tags.isEmpty()) {
            System.out.println("\tNo tags found");
            throw new NotFoundException();
        }

        List<Tag> tagsToReturn = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getUserId().equals(currentUser.getUserId())) {
                tagsToReturn.add(tag);
                continue;
            }

            List<Token> tokens = tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), tag.getTagId());
            if (!tokens.isEmpty()) tagsToReturn.add(tag);
        }
        System.out.println("\treturning " + tagsToReturn.size() + " authorized tags");

        tagsToReturn = tagsToReturn.stream().filter(tag -> !tag.getTitle().contains("} real time")).toList();
        System.out.println("\treturning " + tagsToReturn.size() + " non realtime tags");
        return tagsToReturn;
    }

    @Override
    public @NotNull Tag createTag(UUID authToken, @NotNull TagCreationDTO tag) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());

        if (Objects.isNull(tag.getTitle())) throw new IllegalArgumentException("Tag title is required");
        if (tag.getTitle().contains("} real time")) throw new IllegalArgumentException("Tag title cannot contain } real time");

        Optional<Tag> existingTag = tagsRepository.findByTitle(tag.getTitle());
        if (existingTag.isPresent()) {
            System.out.println("\ttag with title " + tag.getTitle() + " already exists: " + existingTag.get().getTagId());
            throw new ConflictException("Tag with title " + tag.getTitle() + " already exists");
        }

        if (Objects.isNull(tag.getTitle())) throw new IllegalArgumentException("Tag title is required");


        Tag newTag = tagsRepository.saveAndFlush(new Tag(tag, currentUser.getUserId()));
        System.out.println("\tsaved tag: " + newTag.getTagId());
        return newTag;
    }

    @Override
    public void deleteTag(UUID authToken, @NotNull Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());
        Tag tag = checkTagAccessRight(id, currentUser, "delete tag of user ");

        if (tag.getTitle().contains("} real time")) throw new IllegalArgumentException("Cannot delete real time tag");

        Iterable<PlaceTag> placeTags = placesTagsRepository.findByTag_TagId(id);
        placeTags.forEach(placeTag -> System.out.println("\t\tdeleting placeTag: " + placeTag.getPlaceTagId()));
        placesTagsRepository.deleteAll(placeTags);
        System.out.println("\tdeleted " + placeTags.spliterator().getExactSizeIfKnown() + " placeTags");

        List<Token> tokens = tokensRepository.findByTagId(id);
        tokens.forEach(token -> System.out.println("\t\tdeleting token: " + token.getTokenId()));
        tokensRepository.deleteAll(tokens);
        System.out.println("\tdeleted " + tokens.size() + " tokens");

        tagsRepository.delete(tag);
        System.out.println("\tdeleted tag: " + tag.getTagId());
    }

    @Override
    public @NotNull Tag updateTag(UUID authToken, @NotNull Long id, @NotNull TagUpdateDTO tag) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());
        Tag tagToUpdate = checkTagAccessRight(id, currentUser, "update tag of user ");

        if (tag.getDescription() != null) {
            System.out.println("\tupdating description to: " + tag.getDescription() + " for tag: " + tagToUpdate.getTagId());
            tagToUpdate.setDescription(tag.getDescription());
        }

        Tag updatedTag = tagsRepository.saveAndFlush(tagToUpdate);
        System.out.println("\tupdated tag: " + updatedTag);
        return updatedTag;
    }

    @Override
    public void removePlaceFromTag(UUID authToken, @NotNull Long id, @NotNull RemovePlaceFromTagDTO placeId) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());
        checkTagAccessRight(id, currentUser, "remove place from tag of user ");

        PlaceTag placeTag = placesTagsRepository.findByTag_TagIdAndPlace_PlaceId(id, placeId.getPlaceId()).orElseThrow(NotFoundException::new);
        System.out.println("\tdeleting placeTag: " + placeTag.getPlaceTagId());

        placesTagsRepository.delete(placeTag);
        System.out.println("\tdeleted placeTag");
    }

    @Override
    public Tag getTagByPlaceId(UUID authToken, Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        System.out.println("\twith user: " + currentUser.getUserId());
        PlaceTag placeTag = placesTagsRepository.findById(id).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched placeTag: " + placeTag);

        if (!currentUser.getUserId().equals(placeTag.getTag().getUserId())) {
            System.out.println("\t\tuser " + currentUser.getUserId() + " is not the owner of the tag " + placeTag.getTag().getTagId() + " user " + placeTag.getTag().getUserId() + " is");
            List<Token> tokens = tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), placeTag.getTag().getTagId());
            System.out.println("\t\t\tfetched " + tokens.size() + " tokens");
            if (tokens.isEmpty()) {
                System.out.println("\t\t\tuser " + currentUser.getUserId() + " has no token for tag " + placeTag.getTag().getTagId());
                throw new UnauthorizedException(currentUser.getNickname(), "get tag of user " + placeTag.getTag().getUserId());
            }
            System.out.println("\t\t\tuser has access token: " + tokens.get(0).getTokenId());
        }

        return placeTag.getTag();
    }

    @NotNull
    private Tag checkTagAccessRight(@NotNull Long id, User currentUser, String x) {
        Tag tagToUpdate = tagsRepository.findById(id).orElseThrow(NotFoundException::new);
        System.out.println("\tfetched tag: " + tagToUpdate.getTagId());

        if (!currentUser.getUserId().equals(tagToUpdate.getUserId())) {
            System.out.println("\t\tuser " + currentUser.getUserId() + " is not the owner of the tag " + tagToUpdate.getTagId() + " user " + tagToUpdate.getUserId() + " is");
            List<Token> token = tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), id);
            System.out.println("\t\t\tfetched " + token.size() + " tokens");
            if (token.stream().noneMatch(t -> t.getAccessRight().equals(Right.WRITER))) {
                System.out.println("\t\t\tuser " + currentUser.getUserId() + " has no WRITE token for tag " + tagToUpdate.getTagId());
                throw new UnauthorizedException(currentUser.getNickname(), x + tagToUpdate.getUserId());
            }
            System.out.println("\t\t\tuser has access token: " + token.get(0).getTokenId());
        }
        return tagToUpdate;
    }
}

