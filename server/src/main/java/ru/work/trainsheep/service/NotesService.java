package ru.work.trainsheep.service;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.work.trainsheep.entity.FavoriteNote;
import ru.work.trainsheep.entity.NoteEntity;
import ru.work.trainsheep.entity.Tag;
import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.repository.FavoriteNoteRepository;
import ru.work.trainsheep.repository.NoteRepository;
import ru.work.trainsheep.send.SetFavoriteVacancyRequest;
import ru.work.trainsheep.send.VacancyRequest;
import ru.work.trainsheep.send.VacancyResult;
import ru.work.trainsheep.send.VacancyNote;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class NotesService {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    TagService tags;

    @Autowired
    FavoriteNoteRepository favoriteNoteRepository;

    Logger log = LoggerFactory.getLogger(getClass());



    private NoteEntity createForm(VacancyNote note, User user){
        val entity = new NoteEntity(new HashSet<>(), note.getHeader(), note.getContent(),user, note.getSalary(),
                note.getCity(), note.getWorkingHours(), note.isFurtherCooperation(),
                note.isContractualSalary(), note.isDistanceWork());
        for (val textTag : note.getTags()){
            Tag tag = tags.findOrCreate(textTag);
            //tag.getNoteEntities().add(entity);
            entity.getTags().add(tag);
        }
        return entity;
    }

    public VacancyNote createAndSave(User user, VacancyNote note){
        val entity = createForm(note, user);
        noteRepository.save(entity);
        log.info("save note " + entity);
        return entity.toNote(false);
    }



    public VacancyResult getVacancyResult(VacancyRequest request){
        val page = noteRepository.findAll(PageRequest.of(
                request.getPage(),
                request.getCountNotesOnPage(),
                Sort.by("dateCreate").descending()
        ));
        return new VacancyResult(
                page.get().map(NoteEntity::toNote).collect(Collectors.toList()),
                page.getNumber(),
                (int) page.getTotalElements(),
                request.getCountNotesOnPage()
        );
    }

    public VacancyResult getVacancyResultWithLogin(VacancyRequest request, User user){
        val page = noteRepository.findByHeaderContainingIgnoreCase(request.getText(), PageRequest.of(
                request.getPage(),
                request.getCountNotesOnPage(),
                Sort.by("dateCreate").descending()
        ));

        val favorites = noteRepository.favorites(user, Pageable.unpaged()).toSet();
        if (request.getTags().size() > 0){
            val list = page.stream().filter(note -> note.toNote().getTags().containsAll(request.getTags()))
                    .skip((long) request.getPage() * request.getCountNotesOnPage())
                    .limit(request.getCountNotesOnPage())
                    .map(e -> e.toNote(favorites.contains(e)))
                    .collect(Collectors.toList());
            return new VacancyResult(list,
                    page.getNumber(),
                    (int) page.getTotalElements(),
                    request.getCountNotesOnPage());
        }

        return new VacancyResult(
                page.get().map(e -> e.toNote(favorites.contains(e))).collect(Collectors.toList()),
                page.getNumber(),
                (int) page.getTotalElements(),
                request.getCountNotesOnPage()
        );
    }

    public VacancyResult getFavoriteVacancies(VacancyRequest request, User user){
        val page = noteRepository.favorites(user, PageRequest.of(
                request.getPage(),
                request.getCountNotesOnPage(),
                Sort.by("dateCreate").descending()
        ));
        return new VacancyResult(
                page.get().map(e -> e.toNote(true)).collect(Collectors.toList()),
                page.getNumber(),
                (int) page.getTotalElements(),
                request.getCountNotesOnPage()
        );
    }

    @Transactional
    public VacancyNote setFavorite(User user, SetFavoriteVacancyRequest request){
        val note = noteRepository.findById(request.getId());
        if (request.isFavorite()){
            favoriteNoteRepository.save(new FavoriteNote(user, note));
            return note.toNote(true);
        }
        favoriteNoteRepository.deleteByUserAndNote(user, note);
        return note.toNote(false);
    }

    public void remove(VacancyNote note) {
        noteRepository.deleteById(note.getId());
    }
}
