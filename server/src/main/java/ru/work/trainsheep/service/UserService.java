package ru.work.trainsheep.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.work.trainsheep.entity.Role;
import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.entity.UserPasswords;
import ru.work.trainsheep.repository.UserRepository;
import ru.work.trainsheep.send.ChatBlock;
import ru.work.trainsheep.send.SearchChatsRequest;
import ru.work.trainsheep.send.UserRegistrationData;

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
    ChatService chatService;

    List<User> companies = new ArrayList<>();


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

        user.setRegistrationDate(new Date());
        userRepository.save(user);
        if (user.getId() <= 4 && user.isCompany()) {
            companies.add(user);
        }
        if (!user.isCompany())
            chatService.createStartMessagesFor(user, companies);
        return user;
    }
}
