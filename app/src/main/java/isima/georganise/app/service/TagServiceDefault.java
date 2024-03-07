package isima.georganise.app.service;


import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceDefault implements TagService{
    @Autowired
    TagsRepository tagsRepository;
    @Override
    public List<Tag> tags() {
        return tagsRepository.findAll();
    }

    @Override
    public Tag tag(Long id) {
        Optional<Tag> op = tagsRepository.findById(id);
        if(op.isPresent()) {
            return op.get();
        }
        else {
            return null;
        }
    }

    @Override
    public Tag addTag(Tag tag) {
        return tagsRepository.save(tag);
    }

    @Override
    public boolean deleteTag(Long id) {
        Optional<Tag> u = tagsRepository.findById(id);
        if (u.isPresent()) {
            tagsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Optional<Tag> optionalTag = tagsRepository.findById(id);

        if (optionalTag.isPresent()) {
            Tag existingTag = optionalTag.get();

            if(tag.getDescription() != null)
                existingTag.setDescription(tag.getDescription());
            if(tag.getTitle() != null)
                existingTag.setTitle(tag.getTitle());

            return tagsRepository.save(existingTag);
        } else {
            return null;
        }
    }
}

