package isima.georganise.app.service;

import isima.georganise.app.entity.dao.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    public List<Tag> tags();
    public Tag getTagById(Long id);
    public Tag getTagByKeyword(String keyword);
    public Tag addTag(Tag tag);
    public void deleteTag(Long id);
    public Tag updateTag(Long id,Tag tag);
}
