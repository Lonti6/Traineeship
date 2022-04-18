package ru.work.trainsheep.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Message {

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
    private String text;

    @NonNull
    private Date dateCreate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Message message = (Message) o;
        return id ==  message.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
