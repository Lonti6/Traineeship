package ru.work.trainsheep.entity;

import lombok.*;
import ru.work.trainsheep.send.UserData;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthdate;
    private Date registrationDate;
    @NonNull
    private String email;
    private boolean isStudent;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_id", referencedColumnName = "id")
    private Resume resume;

    public User(String firstName, String lastName, String patronymic, Date birthdate, Date registrationDate, @NonNull String email, boolean isStudent, @NonNull Resume resume) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.registrationDate = registrationDate;
        this.email = email;
        this.isStudent = isStudent;
        this.resume = resume;
    }

    public static User from(UserData data){
        Resume resume = new Resume( data.getUniversity(), data.getSpecialization(), data.getYear());
        return new User(data.getFirstName(), data.getLastName(), data.getPatronymic(), data.getBirthdate(), data.getRegistrationDate(), data.getEmail(), data.isStudent(), resume);
    }
}
