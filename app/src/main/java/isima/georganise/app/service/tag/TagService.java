package isima.georganise.app.service.tag;

import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TagUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

    public List<Tag> getAllTags();

    public Tag getTagById(Long id);

    public Iterable<Tag> getTagsByKeyword(String keyword);

    public Tag createTag(TagCreationDTO tag);

    public void deleteTag(Long id);

    public Tag updateTag(Long id, TagUpdateDTO tag);

}
