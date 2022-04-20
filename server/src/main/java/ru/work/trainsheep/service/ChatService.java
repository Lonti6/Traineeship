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
import java.util.stream.Collectors;

@Service
public class ChatService {
    Logger log = LoggerFactory.getLogger(getClass());
    Set<Long> set = new HashSet<>();

    @Autowired
    MessageRepository messages;

    @Autowired
    ChatRepository chats;

    public ChatResult getMessages(User sender, User recipient, int page, int counts){
        if (sender == null || recipient == null){
            log.error("sender or recipient is null " + sender + " " + recipient);
            return null;
        }

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
        if (sender == null || recipient == null){
            log.error("sender or recipient is null " + sender + " " + recipient);
            return null;
        }
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
        if (!set.contains(sender.getId()) && set.contains(recipient.getId())){
            sendMessage(recipient, sender, getRandom(answers));
        }
        return new ChatMessage(sender.getEmail(), message.getText(), message.getDateCreate().getTime());
    }

    public void createStartMessagesFor(User user, List<User> companies){
        log.info("create start message for " + user.getFirstName() + " " + companies.stream().map(User::getFirstName).collect(Collectors.toList()));
        for(User company : companies){
            set.add(company.getId());
            sendMessage(company, user, "Здравствуйте!");
        }
        for(User company : companies){
            sendMessage(company, user, getRandom(hies));
        }
    }
    private static String hies[] = {
            "У нас есть для вас предложение",
            "Вы не заняты?",
            "У нас есть интересная вакансия для вас",
            "Не могли бы вы ответить на несколько вопросов?"
    };

    private static String answers[] = {
            "Ожидайте, сейчас с вами свяжется нас специалист",
            "Благодарим за интерес, скоро вам ответят",
            "Ожидайте ответ на полученный нами запрос",
            "Ваше сообщение несомненно важно для нас!",
            "Ожидайте, сотрудник свяжется с вами"
    };
    private static Random random = new Random();
    private static <T> T getRandom(T [] arr){
        return arr[random.nextInt(arr.length)];
    }
}
