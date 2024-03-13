package isima.georganise.app.service.tag;

import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TagUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TagService {

    public List<Tag> getAllTags(UUID authToken);

    public Tag getTagById(UUID authToken, Long id);

    public Iterable<Tag> getTagsByKeyword(UUID authToken, String keyword);

    public Tag createTag(UUID authToken, TagCreationDTO tag);

    public void deleteTag(UUID authToken, Long id);

    public Tag updateTag(UUID authToken, Long id, TagUpdateDTO tag);

}
