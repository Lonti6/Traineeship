package ru.work.trainsheep.service;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.work.trainsheep.entity.Chat;
import ru.work.trainsheep.entity.Message;
import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.repository.ChatRepository;
import ru.work.trainsheep.repository.MessageRepository;
import ru.work.trainsheep.send.ChatBlock;
import ru.work.trainsheep.send.ChatMessage;
import ru.work.trainsheep.send.ChatResult;

import java.util.*;

@Service
public class ChatService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MessageRepository messages;

    @Autowired
    ChatRepository chats;

    public ChatResult getMessages(User sender, User recipient, int page, int counts){

        val sort = Sort.by("dateCreate").descending();
        val pageable = PageRequest.of(page, counts, sort);
        val pages = messages.findAllBy(sender, recipient, pageable);
        val chat = chats.findBySenderAndRecipient(sender, recipient);
        if (chat != null){
            chat.clearUnreadMessages();
            chats.save(chat);
        }
        var list = new ArrayList<>(pages.map(Message::toChatMessage).toList());

        Collections.reverse(list);
        return new ChatResult(list, pages.getNumber(), (int) pages.getTotalElements(), recipient.getFirstName(), recipient.getImage());

    }

    public List<ChatBlock> getChats(String email){
        return chats.findAllBySender_Email(email).map(Chat::toChatBlock).toList();
    }

    public ChatMessage sendMessage(User sender, User recipient, String text){
        Chat chat = chats.findBySenderAndRecipient(sender, recipient);
        val date = new Date();
        if (chat == null){
            chat = new Chat(sender, recipient, text, 1, date);
        } else {
            chat.addUnreadMessage(text, date);
        }
        chats.save(chat);
        val message = new Message(sender, recipient, text, date);
        messages.save(message);
        log.info("save message " + message);
        return new ChatMessage(sender.getEmail(), message.getText(), message.getDateCreate().getTime());
    }
}
