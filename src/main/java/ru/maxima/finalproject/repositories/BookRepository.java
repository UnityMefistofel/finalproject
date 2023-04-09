package ru.maxima.finalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.finalproject.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    @Override
    Optional<Book> findById(Long aLong);

    Optional<Book> findByName(String name);

    List<Book> getAllByRemovedFalse();
}
