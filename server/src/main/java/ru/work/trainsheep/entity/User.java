package ru.work.trainsheep.entity;

import lombok.*;
import org.hibernate.Hibernate;
import ru.work.trainsheep.send.UserData;
import ru.work.trainsheep.service.TagService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private boolean isCompany;
    @NonNull
    private String image;
    @NonNull
    private String phone;
    @NonNull
    private String university;
    @NonNull
    private String specialization ;
    @NonNull
    private String city;
    @NonNull
    private String description ;
    @NonNull
    private int curs;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "have_tags",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ToString.Exclude
    private Set<Tag> tags;




    public static User from(UserData data, TagService tagService){

        val user = new User(data.getFirstName(), data.getLastName(), data.getPatronymic(), data.getBirthdate(),
                data.getRegistrationDate(), data.getEmail(), data.isCompany(), data.getAvatarSrc(),
                data.getPhoneNumber(), data.getUniversity(), data.getSpecialization(), data.getCity(), data.getDescription(), data.getCurs());

        val tags = data.getCompetencies().stream().map(tagService::findOrCreate).collect(Collectors.toSet());
        user.setTags(tags);

        return user;
    }

    public UserData toUserDate(){
        return new UserData(firstName, lastName, patronymic, birthdate.getTime(), registrationDate.getTime(), email,
                phone, image, tags.stream().map(Tag::getText).collect(Collectors.toList()), isCompany, getUniversity(),
                getSpecialization(), getCity(), getDescription(), getCurs());
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
