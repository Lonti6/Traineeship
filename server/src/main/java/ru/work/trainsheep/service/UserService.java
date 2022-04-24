package ru.work.trainsheep.service;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.work.trainsheep.entity.Role;
import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.entity.UserPasswords;
import ru.work.trainsheep.repository.UserRepository;
import ru.work.trainsheep.send.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordsService passwordsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TagService tagService;

    @Autowired
    ChatService chatService;

    List<User> companies = new ArrayList<>();
    Logger log = LoggerFactory.getLogger(getClass());


    public boolean existUser(User user) {
        return userRepository.findByEmail(user.getEmail()) != null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        val target = new ArrayList<User>();
        userRepository.findAll().forEach(target::add);
        return target;
    }

    public List<ChatBlock> findUsersForChat(User user, SearchChatsRequest request) {
        val page = userRepository.findUsersWith(request.getText(), PageRequest.of(
                request.getPage(),
                request.getCountChatsOnPage(),
                Sort.by("firstName").descending()
        ));
        return page.filter(u -> user.getId() != u.getId()).map((u) -> new ChatBlock(
                u.getFirstName(),
                u.getEmail(),
                "",
                u.getImage(),
                0,
                new Date().getTime())).toList();
    }

    public CompanyResult getCompanies(CompanyRequest request){
        val page = userRepository.findAllByIsCompany(true, PageRequest.of(
                request.getPage(),
                request.getCountNotesOnPage(),
                Sort.by("id").descending()
        ));
        val list = page.map((user) ->
            new CompanyNote(user.getId(), user.getFirstName(), user.getDescription(), user.getImage(), user.getEmail())
        ).toList();
        return new CompanyResult(list, page.getNumber(),
                (int) page.getTotalElements(),
                request.getCountNotesOnPage());

    }


    public User register(UserRegistrationData userData) {
        val old = findByEmail(userData.getEmail());
        if (old != null)
            return null;
        val userPass = new UserPasswords(userData.getEmail(), passwordEncoder.encode(userData.getPassword()), Role.USER.toString());
        passwordsService.create(userPass);
        val user = new User();
        user.setFirstName(userData.getName());
        user.setEmail(userData.getEmail());
        user.setCompany(userData.isCompany());
        user.setLastName(userData.getLastName());
        user.setBirthdate(new Date(0));
        user.setCompany(userData.isCompany());

        user.setRegistrationDate(new Date());
        userRepository.save(user);

        log.info("register " + user.getId() + " " + user.getFirstName() + " " + user.getEmail() + " " + user.isCompany());
        if (user.getId() <= 4 && user.isCompany()) {
            companies.add(user);
        }
        if (!user.isCompany())
            chatService.createStartMessagesFor(user, companies);
        return user;
    }

    public void updateUser(User user, UserData request) {
        val userUpdate = User.from(request, tagService);

        if (!userUpdate.getFirstName().equals("")){
            user.setFirstName(userUpdate.getFirstName());
        }

        if (!userUpdate.getLastName().equals("")){
            user.setLastName(userUpdate.getLastName());
        }

        if (!userUpdate.getPatronymic().equals("")){
            user.setPatronymic(userUpdate.getPatronymic());
        }

        if (!userUpdate.getImage().equals("")){
            user.setImage(userUpdate.getImage());
        }
        user.setTags(userUpdate.getTags());
        user.setCompany(userUpdate.isCompany());
        user.setBirthdate(userUpdate.getBirthdate());
        user.setPhone(userUpdate.getPhone());
        user.setDescription(userUpdate.getDescription());
        user.setCurs(userUpdate.getCurs());
        user.setUniversity(userUpdate.getUniversity());

        userRepository.save(user);

        log.info("update user : " + user);
    }
}
