package isima.georganise.app.service;

import isima.georganise.app.entity.dao.Tag;

import java.util.List;

public interface TagService {
    public List<Tag> tags();
    public Tag tag(Long id);
    public Tag addTag(Tag tag);
    public boolean deleteTag(Long id);
    public Tag updateTag(Long id,Tag tag);
}
