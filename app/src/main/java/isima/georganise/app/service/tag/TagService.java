package isima.georganise.app.service.tag;

import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dto.RemovePlaceFromTagDTO;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TagUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * This interface represents the TagService.
 * It provides methods to interact with the Tag entity.
 */
@Service
public interface TagService {

    /**
     * This method retrieves all tags for a given user.
     * It returns a List of Tag.
     *
     * @param authToken The authentication token of the user.
     * @return A List of Tag.
     */
    List<Tag> getAllTags(UUID authToken);

    /**
     * This method retrieves a tag by its id for a given user.
     * It returns a Tag.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     * @return A Tag.
     */
    Tag getTagById(UUID authToken, Long id);

    /**
     * This method retrieves tags by keyword for a given user.
     * It returns an Iterable of Tag.
     *
     * @param authToken The authentication token of the user.
     * @param keyword The keyword to search for in the tags.
     * @return An Iterable of Tag.
     */
    Iterable<Tag> getTagsByKeyword(UUID authToken, String keyword);

    /**
     * This method creates a tag for a given user.
     * It returns a Tag.
     *
     * @param authToken The authentication token of the user.
     * @param tag The TagCreationDTO containing the data to create the tag.
     * @return A Tag.
     */
    Tag createTag(UUID authToken, TagCreationDTO tag);

    /**
     * This method deletes a tag by its id for a given user.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     */
    void deleteTag(UUID authToken, Long id);

    /**
     * This method updates a tag by its id for a given user.
     * It returns a Tag.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     * @param tag The TagUpdateDTO containing the data to update the tag.
     * @return A Tag.
     */
    Tag updateTag(UUID authToken, Long id, TagUpdateDTO tag);

    /**
     * This method removes a place from a tag by its id for a given user.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the tag.
     * @param placeId The RemovePlaceFromTagDTO containing the id of the place to be removed.
     */
    void removePlaceFromTag(UUID authToken, Long id, RemovePlaceFromTagDTO placeId);

    /**
     * This method retrieves a tag by place id for a given user.
     * It returns a Tag.
     *
     * @param authToken The authentication token of the user.
     * @param id The id of the place.
     * @return A Tag.
     */
    Tag getTagByPlaceId(UUID authToken, Long id);
}
