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
import isima.georganise.app.repository.PlacesTagsRepository;
import isima.georganise.app.repository.TagsRepository;
import isima.georganise.app.repository.TokensRepository;
import isima.georganise.app.repository.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * This class implements the TagService interface.
 * It provides methods to interact with the Tag entity.
 */
@Service
public class TagServiceImpl implements TagService {

    private final @NotNull TagsRepository tagsRepository;

    private final @NotNull UsersRepository usersRepository;

    private final @NotNull TokensRepository tokensRepository;

    private final @NotNull PlacesTagsRepository placesTagsRepository;

    /**
     * This constructor initializes the TagServiceImpl with the necessary repositories.
     *
     * @param tagsRepository The repository to interact with the Tag entity.
     * @param usersRepository The repository to interact with the User entity.
     * @param tokensRepository The repository to interact with the Token entity.
     * @param placesTagsRepository The repository to interact with the PlaceTag entity.
     */
    @Autowired
    public TagServiceImpl(@NotNull TagsRepository tagsRepository, @NotNull UsersRepository usersRepository, @NotNull TokensRepository tokensRepository, @NotNull PlacesTagsRepository placesTagsRepository) {
        Assert.notNull(tagsRepository, "tagsRepository must not be null");
        Assert.notNull(usersRepository, "usersRepository must not be null");
        Assert.notNull(tokensRepository, "tokensRepository must not be null");
        Assert.notNull(placesTagsRepository, "placesTagsRepository must not be null");
        this.tagsRepository = tagsRepository;
        this.usersRepository = usersRepository;
        this.tokensRepository = tokensRepository;
        this.placesTagsRepository = placesTagsRepository;
    }

    /**
     * This method retrieves all tags for a given user.
     * It returns a List of Tag.
     *
     * @param authToken The authentication token of the user.
     * @return A List of Tag.
     */
    @Override
    public @NotNull List<Tag> getAllTags(UUID authToken) {
        User userCurrent = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        List<Tag> tags = tagsRepository.findAll();

        if (tags.isEmpty()) throw new NotFoundException();

        List<Tag> tagsToReturn = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getUserId().equals(userCurrent.getUserId())) {
                tagsToReturn.add(tag);
            } else {
                List<Token> tokens = tokensRepository.findByUserIdAndTagId(userCurrent.getUserId(), tag.getTagId());
                if (tokens.isEmpty() || tokens.stream().noneMatch(t -> t.getAccessRight().equals(Right.WRITER))) continue;
                tagsToReturn.add(tag);
            }
        }

        tagsToReturn = tagsToReturn.stream().filter(tag -> !tag.getTitle().contains("} real time")).toList();

        return tagsToReturn;
    }

    /**
     * This method retrieves a tag by its id for a given user.
     * It returns a Tag.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     * @return A Tag.
     */
    @Override
    public @NotNull Tag getTagById(UUID authToken, @NotNull Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Tag tag = tagsRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!currentUser.getUserId().equals(tag.getUserId()) && (tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), tag.getTagId()).isEmpty()))
            throw new UnauthorizedException(currentUser.getNickname(), "get tag of user " + tag.getUserId());

        return tag;
    }

    /**
     * This method retrieves tags by keyword for a given user.
     * It returns an Iterable of Tag.
     *
     * @param authToken The authentication token of the user.
     * @param keyword The keyword to search for in the tags.
     * @return An Iterable of Tag.
     */
    @Override
    public @NotNull Iterable<Tag> getTagsByKeyword(UUID authToken, String keyword) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        List<Tag> tags = tagsRepository.findByKeyword(keyword);

        if (tags.isEmpty()) throw new NotFoundException();

        List<Tag> tagsToReturn = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getUserId().equals(currentUser.getUserId())) {
                tagsToReturn.add(tag);
            } else {
                List<Token> tokens = tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), tag.getTagId());
                if (!tokens.isEmpty() && tokens.stream().anyMatch(t -> t.getAccessRight().equals(Right.WRITER)))
                    tagsToReturn.add(tag);
            }
        }

        tagsToReturn = tagsToReturn.stream().filter(tag -> !tag.getTitle().contains("} real time")).toList();
        return tagsToReturn;
    }

    /**
     * This method creates a tag for a given user.
     * It returns a Tag.
     *
     * @param authToken The authentication token of the user.
     * @param tag The TagCreationDTO containing the data to create the tag.
     * @return A Tag.
     */
    @Override
    public @NotNull Tag createTag(UUID authToken, @NotNull TagCreationDTO tag) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);

        if (Objects.isNull(tag.getTitle())) throw new IllegalArgumentException("Tag title is required");
        if (tag.getTitle().contains("} real time")) throw new IllegalArgumentException("Tag title cannot contain } real time");

        if (tagsRepository.findByTitle(tag.getTitle()).isPresent()) throw new ConflictException("Tag with title " + tag.getTitle() + " already exists");

        if (Objects.isNull(tag.getTitle())) throw new IllegalArgumentException("Tag title is required");

        return tagsRepository.saveAndFlush(new Tag(tag, currentUser.getUserId()));
    }

    /**
     * This method deletes a tag by its id for a given user.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     */
    @Override
    public void deleteTag(UUID authToken, @NotNull Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Tag tag = checkTagAccessRight(id, currentUser, "delete tag of user ");

        if (tag.getTitle().contains("} real time")) throw new IllegalArgumentException("Cannot delete real time tag");

        Iterable<PlaceTag> placeTags = placesTagsRepository.findByTag_TagId(id);
        placesTagsRepository.deleteAll(placeTags);

        List<Token> tokens = tokensRepository.findByTagId(id);
        tokensRepository.deleteAll(tokens);

        tagsRepository.delete(tag);
    }

    /**
     * This method updates a tag by its id for a given user.
     * It returns a Tag.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     * @param tag The TagUpdateDTO containing the data to update the tag.
     * @return A Tag.
     */
    @Override
    public @NotNull Tag updateTag(UUID authToken, @NotNull Long id, @NotNull TagUpdateDTO tag) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        Tag tagToUpdate = checkTagAccessRight(id, currentUser, "update tag of user ");

        if (tag.getDescription() != null) tagToUpdate.setDescription(tag.getDescription());

        return tagsRepository.saveAndFlush(tagToUpdate);
    }

    /**
     * This method removes a place from a tag by its id for a given user.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     * @param placeId The RemovePlaceFromTagDTO containing the id of the place to be removed.
     */
    @Override
    public void removePlaceFromTag(UUID authToken, @NotNull Long id, @NotNull RemovePlaceFromTagDTO placeId) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        checkTagAccessRight(id, currentUser, "remove place from tag of user ");

        placesTagsRepository.delete(placesTagsRepository.findByTag_TagIdAndPlace_PlaceId(id, placeId.getPlaceId()).orElseThrow(NotFoundException::new));
    }

    /**
     * This method retrieves a tag by place id for a given user.
     * It returns a Tag.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place.
     * @return A Tag.
     */
    @Override
    public Tag getTagByPlaceId(UUID authToken, @NotNull Long id) {
        User currentUser = usersRepository.findByAuthToken(authToken).orElseThrow(NotLoggedException::new);
        PlaceTag placeTag = placesTagsRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!currentUser.getUserId().equals(placeTag.getTag().getUserId()) && (tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), placeTag.getTag().getTagId()).isEmpty()))
            throw new UnauthorizedException(currentUser.getNickname(), "get tag of user " + placeTag.getTag().getUserId());

        return placeTag.getTag();
    }

    /**
     * This method checks if the user has access rights to the tag.
     * It returns a Tag.
     *
     * @param id The id of the tag.
     * @param currentUser The current User.
     * @param x The string to be used in the UnauthorizedException message.
     * @return A Tag.
     */
    @NotNull
    private Tag checkTagAccessRight(@NotNull Long id, @NotNull User currentUser, String x) {
        Tag tagToUpdate = tagsRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!currentUser.getUserId().equals(tagToUpdate.getUserId())) {
            List<Token> token = tokensRepository.findByUserIdAndTagId(currentUser.getUserId(), id);
            if (token.stream().noneMatch(t -> t.getAccessRight().equals(Right.WRITER))) throw new UnauthorizedException(currentUser.getNickname(), x + tagToUpdate.getUserId());
        }

        return tagToUpdate;
    }
}