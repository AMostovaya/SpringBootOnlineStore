package ru.geekbrains.repo;

import ru.geekbrains.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Page<User> findByAgeGreaterThanEqual(Integer age, Pageable pageable);

    Page<User> findByAgeLessThanEqual(Integer age, Pageable pageable);

    Page<User> findByAgeBetween(Integer minAge, Integer maxAge, String nameSearch, Pageable pageable);

    @Query("from User u where u.name like CONCAT('%',:pattern,'%')")
    Page<User> queryByNamePattern(@Param("pattern") String pattern,   Pageable pageable);

    Optional<User> findUserByName(String nameUser);

}

