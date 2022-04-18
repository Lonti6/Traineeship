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

    public boolean addUser (User user){
        if(!existUser(user)) {
            user.setRegistrationDate(new Date());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean existUser(User user){
        return userRepository.findByEmail(user.getEmail()) != null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean register(String email, String pass, String name){
        val old = findByEmail(email);
        if (old != null)
            return false;
        val userPass = new UserPasswords(email, passwordEncoder.encode(pass), Role.USER.toString());
        passwordsService.create(userPass);
        val userbd = new User();
        userbd.setFirstName(name);
        userbd.setEmail(email);
        return addUser(userbd);
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
