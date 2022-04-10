package ru.work.trainsheep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.work.trainsheep.entity.NoteEntity;
import ru.work.trainsheep.entity.Tag;

import java.util.Set;

public interface NoteRepository extends CrudRepository<NoteEntity, Long> {

    NoteEntity findById(long id);
    Set<NoteEntity> findAllByHeaderStartingWith(String header);
    //Set<NoteEntity> findAllByTagsContains(Set<Tag> tags);
    Page<NoteEntity> findAll(Pageable pageable);
}
