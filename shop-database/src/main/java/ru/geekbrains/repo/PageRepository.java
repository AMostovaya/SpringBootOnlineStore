package ru.geekbrains.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.model.User;

import java.util.List;

@Repository
public interface PageRepository extends PagingAndSortingRepository <User, Long>{

    List<User> findAllBy(Pageable pageable);
}
