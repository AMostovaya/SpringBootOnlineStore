package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.model.User;
import ru.geekbrains.repo.UserRepository;
import ru.geekbrains.repo.UserSpecification;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    //private  PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {

        return userRepository.findById(id);
    }

    public Page<User> findByAgeName(Integer minAge, Integer maxAge, String nameSearch, Pageable pageable) {

        Specification<User> specification = UserSpecification.trueLiteral();

        if (minAge!=null) {
            specification = specification.and(UserSpecification.ageGreaterThanOEqual(minAge));
        }

        if (maxAge!=null) {
            specification = specification.and(UserSpecification.ageLessThanOEqual(maxAge));
        }

        if (nameSearch!=null && !nameSearch.isEmpty()) {
            specification = specification.and(UserSpecification.findByName(nameSearch));
        }

        return userRepository.findAll(specification, pageable);
   }

    @Transactional
    public void save(User user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword())); // шифруем пароль
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
