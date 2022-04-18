package ru.work.trainsheep.entity;

import lombok.*;
import org.hibernate.Hibernate;
import ru.work.trainsheep.send.UserData;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity
@Table(
        name = "user",
        indexes = {
                @Index(name = "email_index", columnList = "email", unique = true)
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String patronymic;
    @NonNull
    private Date birthdate;
    @NonNull
    private Date registrationDate;
    @NonNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @NonNull
    private boolean isStudent;

    private String image;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_id", referencedColumnName = "id")
    private Resume resume;


    public static User from(UserData data){
        Resume resume = new Resume( data.getUniversity(), data.getSpecialization(), data.getYear());
        return new User(data.getFirstName(), data.getLastName(), data.getPatronymic(), data.getBirthdate(), data.getRegistrationDate(), data.getEmail(), data.isStudent(), resume);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
