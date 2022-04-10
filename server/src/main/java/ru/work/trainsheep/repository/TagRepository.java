package ru.work.trainsheep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.work.trainsheep.entity.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByTextIgnoreCase(String text);

}