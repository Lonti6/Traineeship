package ru.work.trainsheep.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;
import ru.work.trainsheep.send.Note;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "have_tags",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @NonNull
    @Column(length = 500)
    private String header;

    @NonNull
    @Column(length = 4000)
    private String content;

    @NonNull
    @Column(length = 500)
    private String company;

    @NonNull
    @Column(length = 100)
    private String salary;

    @NonNull
    private Date dateCreate = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NoteEntity noteEntity = (NoteEntity) o;
        return id == noteEntity.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "header = " + header + ", " +
                "content = " + content + ", " +
                "company = " + company + ", " +
                "date = " + dateCreate + ", " +
                "salary = " + salary + ")";
    }

    public Note toNote(){
        return new Note(tags.stream().map(Tag::getText).collect(Collectors.toList()), header, content, company, salary);
    }
}
