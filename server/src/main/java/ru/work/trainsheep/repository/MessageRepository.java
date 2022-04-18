package ru.work.trainsheep.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.work.trainsheep.entity.Message;
import ru.work.trainsheep.entity.User;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query(value = "SELECT m FROM Message m WHERE ((m.sender.email = ?1 and m.recipient.email = ?2) or (m.sender.email = ?2 and m.recipient.email = ?1))")
    List<Message> findAllBy(String sender, String recipient);


}
