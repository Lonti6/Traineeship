package ru.work.trainsheep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.repository.UserRepository;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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
}
