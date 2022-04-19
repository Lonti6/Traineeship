package ru.work.trainsheep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.work.trainsheep.entity.NoteEntity;
import ru.work.trainsheep.entity.Tag;
import ru.work.trainsheep.entity.User;

import java.util.Set;

public interface NoteRepository extends CrudRepository<NoteEntity, Long> {

    NoteEntity findById(long id);
    Set<NoteEntity> findAllByHeaderStartingWith(String header);
    //Set<NoteEntity> findAllByTagsContains(Set<Tag> tags);
    Page<NoteEntity> findAll(Pageable pageable);

    @Query(value = "select n from NoteEntity n, FavoriteNote f where f.user = ?1 and n = f.note")
    Page<NoteEntity> favorites(User user, Pageable pageable);
}
