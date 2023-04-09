package ru.maxima.finalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.finalproject.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    @Override
    Optional<Person> findById(Long aLong);

    Optional<Person> findByUsername(String username);

    List<Person> getAllByRemovedFalse();


}
