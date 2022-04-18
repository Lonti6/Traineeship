package ru.work.trainsheep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import ru.work.trainsheep.entity.Chat;
import ru.work.trainsheep.entity.Message;
import ru.work.trainsheep.entity.User;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long> {

     Streamable<Chat> findAllBySender_Email(String sender);

     Chat findBySenderAndRecipient(User sender, User recipient);

}
