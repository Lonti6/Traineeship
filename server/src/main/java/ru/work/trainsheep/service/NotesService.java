package ru.work.trainsheep.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ru.work.trainsheep.entity.NoteEntity;
import ru.work.trainsheep.entity.Tag;
import ru.work.trainsheep.repository.NoteRepository;
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

    public void save(VacancyNote note) {
        val entity = createForm(note);
        noteRepository.save(entity);
    }

    private NoteEntity createForm(VacancyNote note){
        val entity = new NoteEntity(new HashSet<>(), note.getHeader(), note.getContent(), note.getCompany(), note.getSalary());
        for (val textTag : note.getTags()){
            Tag tag = tags.findOrCreate(textTag);
            //tag.getNoteEntities().add(entity);
            entity.getTags().add(tag);
        }
        return entity;
    }



    public VacancyResult getAdvertResult(VacancyRequest request){
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

}
