package ru.work.trainsheep.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;
import ru.work.trainsheep.send.VacancyNote;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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
    @ManyToOne
    private User company;

    @NonNull
    @Column(length = 100)
    private String salary;

    @NonNull
    private Date dateCreate = new Date();

    @NonNull
    String city ;
    @NonNull
    int workingHours;
    @NonNull
    boolean furtherCooperation ;
    @NonNull
    boolean contractualSalary ;
    @NonNull
    boolean distanceWork;

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", content='" + content + '\'' +
                ", company=" + company +
                ", salary='" + salary + '\'' +
                ", city='" + city + '\'' +
                ", workingHours=" + workingHours +
                ", furtherCooperation=" + furtherCooperation +
                ", contractualSalary=" + contractualSalary +
                ", distanceWork=" + distanceWork +
                '}';
    }

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


    public VacancyNote toNote(boolean favorite){
        return new VacancyNote(tags.stream().map(Tag::getText).collect(Collectors.toList()), header, content,
                company.getFirstName(), salary, favorite, id, company.getEmail(), company.getImage(), city, workingHours, furtherCooperation,
                contractualSalary, distanceWork);
    }
    public VacancyNote toNote(){
        return toNote(false);
    }
}
