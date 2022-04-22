package ru.work.trainsheep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.work.trainsheep.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
    User findByEmail(String email);
    @Query(value = "select u from User u where " +
            "lower(u.email) like lower(concat('%', concat(?1, '%'))) or " +
            "lower(u.firstName) like lower(concat('%', concat(?1, '%'))) or " +
            "lower(u.lastName) like lower(concat('%', concat(?1, '%')))")
    Page<User> findUsersWith(String text, Pageable pageable);

    Page<User> findAllByCompany(boolean isCompany, Pageable pageable);
}
