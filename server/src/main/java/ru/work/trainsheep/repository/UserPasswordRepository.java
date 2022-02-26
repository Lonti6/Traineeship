package ru.work.trainsheep.repository;

import org.springframework.data.repository.CrudRepository;

import ru.work.trainsheep.entity.UserPasswords;

public interface UserPasswordRepository extends CrudRepository<UserPasswords, Long> {

    UserPasswords findByUsername(String username);
}
