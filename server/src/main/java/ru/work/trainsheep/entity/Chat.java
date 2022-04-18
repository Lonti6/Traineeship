package ru.work.trainsheep.entity;

import lombok.*;
import org.springframework.lang.NonNull;
import ru.work.trainsheep.send.ChatBlock;
import ru.work.trainsheep.send.ChatMessage;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @NonNull
    @Column(length = 1000)
    String lastMessage;

    @NonNull
    int countUnreadMessages;

    @NonNull
    Date lastMessageDate;

    public ChatBlock toChatBlock() {
        return new ChatBlock(recipient.getFirstName(), recipient.getEmail(), lastMessage, recipient.getImage(), countUnreadMessages, lastMessageDate.getTime());
    }

    public void clearUnreadMessages() {
        countUnreadMessages = 0;
    }
    public void addUnreadMessage(String text, Date date) {
        setLastMessage(text);
        countUnreadMessages += 1;
        setLastMessageDate(date);
    }
}
