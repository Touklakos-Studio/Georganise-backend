package isima.georganise.app.service.tag;


import isima.georganise.app.entity.dao.Tag;
import isima.georganise.app.entity.dto.TagCreationDTO;
import isima.georganise.app.entity.dto.TagUpdateDTO;
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
    public List<Tag> getAllTags() {
        return tagsRepository.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagsRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Iterable<Tag> getTagsByKeyword(String keyword) {
        return tagsRepository.findByKeyword(keyword).orElseThrow(NotFoundException::new);
    }

    @Override
    public Tag createTag(TagCreationDTO tag) {
        return tagsRepository.saveAndFlush(new Tag(tag, 1L));
    }

    @Override
    public void deleteTag(Long id) {
        tagsRepository.delete(tagsRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Tag updateTag(Long id, TagUpdateDTO tag) {
        Tag tagToUpdate = tagsRepository.findById(id).orElseThrow(NotFoundException::new);

        if (tag.getTitle() != null) {
            tagToUpdate.setTitle(tag.getTitle());
        }
        if (tag.getDescription() != null) {
            tagToUpdate.setDescription(tag.getDescription());
        }

        return tagsRepository.saveAndFlush(tagToUpdate);
    }
}

