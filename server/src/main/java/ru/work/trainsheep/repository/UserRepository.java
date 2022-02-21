package ru.work.trainsheep.repository;

import org.springframework.data.repository.CrudRepository;
import ru.work.trainsheep.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
    User findByMail(String mail);
}
