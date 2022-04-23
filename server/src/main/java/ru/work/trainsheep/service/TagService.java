package ru.work.trainsheep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.work.trainsheep.entity.Tag;
import ru.work.trainsheep.repository.TagRepository;

import java.util.HashSet;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public Tag findOrCreate(String text){
        Tag tag = tagRepository.findByTextIgnoreCase(text);
        if (tag == null) {
            tag = new Tag(text);
            tagRepository.save(tag);
        }
        return tag;
    }
}
