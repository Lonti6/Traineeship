package ru.work.trainsheep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.work.trainsheep.entity.FavoriteNote;
import ru.work.trainsheep.entity.NoteEntity;
import ru.work.trainsheep.entity.User;

import java.util.Set;

public interface FavoriteNoteRepository extends CrudRepository<FavoriteNote, Long> {

   void deleteByUserAndNote(User user, NoteEntity note);
}
