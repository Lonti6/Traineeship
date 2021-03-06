package ru.work.trainsheep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import ru.work.trainsheep.entity.Message;
import ru.work.trainsheep.entity.User;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query(value = "SELECT m FROM Message m WHERE ((m.sender = ?1 and m.recipient = ?2) or (m.sender = ?2 and m.recipient = ?1))")
    Page<Message> findAllBy(User sender, User recipient, Pageable pageable);


}
