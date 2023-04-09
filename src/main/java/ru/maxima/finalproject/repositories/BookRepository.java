package ru.maxima.finalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.maxima.finalproject.models.Book;
import ru.maxima.finalproject.models.BookExt;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    @Override
    Optional<Book> findById(Long aLong);

    Optional<Book> findByName(String name);

    List<Book> getAllByRemovedFalse();

    @Query(value = "select b.id,be.id as entity,bh.id as linked," +
            "b.name,b.author,b.year_of_production,b.annotation,be.serialnum,bh.gaveout " +
            "from books b " +
            "   join booksentity be on b.id = be.book_id " +
            "       join booksonhands bh on be.id = bh.bookentity_id " +
            "where b.removed = false " +
            "  and be.removed = false " +
            "  and bh.returned is null " +
            "  and bh.person_id = ?1", nativeQuery = true)
    List<BookExt> getBookListByOwner(Long person_id);
}
