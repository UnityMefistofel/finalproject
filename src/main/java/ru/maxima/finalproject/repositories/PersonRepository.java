package ru.maxima.finalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.maxima.finalproject.models.Book;
import ru.maxima.finalproject.models.BookExt;
import ru.maxima.finalproject.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Override
    Optional<Person> findById(Long aLong);

    Optional<Person> findByUsername(String username);

    List<Person> getAllByRemovedFalse();

    @Query(value = "select count(bookentity_id) from booksonhands where person_id = ?1", nativeQuery = true)
    int getBooksCntByOwner(Long id);

}
