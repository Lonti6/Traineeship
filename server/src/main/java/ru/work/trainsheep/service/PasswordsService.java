package ru.work.trainsheep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.work.trainsheep.repository.UserPasswordRepository;

@Service
public class PasswordsService implements UserDetailsService {

    @Autowired
    UserPasswordRepository userPasswordRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userPasswordRepository.findByUsername(username);
    }
}
