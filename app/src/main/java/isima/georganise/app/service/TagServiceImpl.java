package isima.georganise.app.service;


import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.exception.NotFoundException;
import isima.georganise.app.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    TagsRepository tagsRepository;
    @Override
    public List<Tag> tags() {
        return tagsRepository.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        Optional<Tag> op = tagsRepository.findById(id);
        return op.orElseThrow(NotFoundException::new);
    }

    @Override
    public Tag getTagByKeyword(String keyword) {
        Optional<Tag> op = tagsRepository.findByKeyword(keyword);
        return op.orElseThrow(NotFoundException::new);
    }

    @Override
    public Tag addTag(Tag tag) {
        return tagsRepository.save(tag);
    }

    @Override
    public void deleteTag(Long id) {
        Optional<Tag> u = tagsRepository.findById(id);
        if (u.isEmpty()) throw new NotFoundException();
        tagsRepository.deleteById(id);
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
            throw new NotFoundException();
        }
    }
}

