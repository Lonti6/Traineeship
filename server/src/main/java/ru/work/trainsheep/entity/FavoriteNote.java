package ru.work.trainsheep.entity;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class FavoriteNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    @ManyToOne
    private NoteEntity note;

}
