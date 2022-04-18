package ru.work.trainsheep.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.work.trainsheep.entity.Message;
import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.repository.MessageRepository;
import ru.work.trainsheep.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messages;

    public boolean addUser (User user){
        if(!existUser(user)) {
            user.setRegistrationDate(new Date());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean existUser(User user){
        return userRepository.findByEmail(user.getEmail()) != null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Message> getAllMessages(String emailSender, String emailRecipient, int page, int counts){
        User userSender = findByEmail(emailSender);
        User userRecipient = findByEmail(emailRecipient);
        if (userSender == null || userRecipient == null)
            return List.of();
        val sort = Sort.sort(Message.class).by(Message::getDateCreate).descending();
        val pageable = PageRequest.of(page, counts, sort);

        return messages.findBySenderAndRecipient(userSender, userRecipient, pageable);
    }
}
