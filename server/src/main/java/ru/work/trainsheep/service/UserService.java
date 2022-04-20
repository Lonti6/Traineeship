package ru.work.trainsheep.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.work.trainsheep.entity.*;
import ru.work.trainsheep.repository.UserRepository;

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


    public boolean existUser(User user){
        return userRepository.findByEmail(user.getEmail()) != null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User register(String email, String pass, String name, boolean isCompany){
        val old = findByEmail(email);
        if (old != null)
            return null;
        val userPass = new UserPasswords(email, passwordEncoder.encode(pass), Role.USER.toString());
        passwordsService.create(userPass);
        val user = new User();
        user.setFirstName(name);
        user.setEmail(email);
        user.setCompany(isCompany);

        user.setRegistrationDate(new Date());
        userRepository.save(user);
        if (user.getId() <= 4 && user.isCompany()){
            companies.add(user);
        }
        if (!user.isCompany())
            chatService.createStartMessagesFor(user, companies);
        return user;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public List<User> getAll(){
        val target = new ArrayList<User>();
        userRepository.findAll().forEach(target::add);
        return target;
    }


}
