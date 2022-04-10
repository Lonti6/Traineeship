package ru.work.trainsheep.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ru.work.trainsheep.entity.NoteEntity;
import ru.work.trainsheep.entity.Tag;
import ru.work.trainsheep.repository.NoteRepository;
import ru.work.trainsheep.repository.TagRepository;
import ru.work.trainsheep.send.AdvertRequest;
import ru.work.trainsheep.send.AdvertResult;
import ru.work.trainsheep.send.Note;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotesService {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    TagService tags;

    public void save(Note note) {
        val entity = createForm(note);
        noteRepository.save(entity);
    }

    private NoteEntity createForm(Note note){
        val entity = new NoteEntity(new HashSet<>(), note.getHeader(), note.getContent(), note.getCompany(), note.getSalary());
        for (val textTag : note.getTags()){
            Tag tag = tags.findOrCreate(textTag);
            //tag.getNoteEntities().add(entity);
            entity.getTags().add(tag);
        }
        return entity;
    }



    public AdvertResult getAdvertResult(AdvertRequest request){
        val page = noteRepository.findAll(PageRequest.of(
                request.getPage(),
                request.getCountNotesOnPage(),
                Sort.by("dateCreate").descending()
        ));
        return new AdvertResult(
                page.get().map(NoteEntity::toNote).collect(Collectors.toList()),
                page.getNumber(),
                (int) page.getTotalElements(),
                request.getCountNotesOnPage()
        );
    }

}
