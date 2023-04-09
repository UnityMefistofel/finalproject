package ru.maxima.finalproject.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxima.finalproject.models.Person;
import ru.maxima.finalproject.models.Book;
import ru.maxima.finalproject.repositories.BookRepository;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("/all", bookRepository.getAllByRemovedFalse());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String elem(@PathVariable("id") String id, Model model) {
        model.addAttribute("book",bookRepository.findById(Long.parseLong(id)));
        //model.addAttribute("owner", bookRepository.get.getPersonById(book.getOwner()));
//        if (book.getOwner() == 0) {
//            model.addAttribute("people", bookRepository.getAllPersons());
//        }
        return "books/elem";
    }

    @GetMapping("/create")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") String id, Model model) {
        model.addAttribute("book", bookRepository.findById(Long.parseLong(id)));
        return "books/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("book") Book book) {
        bookRepository.save(book);
        return "redirect:/books"+book.getId();
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        int updateCount = entityManager.createNativeQuery("update books set isDel = :isdel where id = :id",
                        Person.class)
                .setParameter("isdel", true)
                .setParameter("id", Long.parseLong(id))
                .executeUpdate();
        return "redirect:/books";
    }

    @PostMapping("/giveto")
    public String giveBookTo(@ModelAttribute("book") Book book) {
        //bookRepository.setBookToOwner(book.getId(), book.getOwner());
        return "redirect:/books/"+book.getId();
    }

    @GetMapping("/{id}/return")
    public String returnBook(@PathVariable("id") String id) {
       // bookRepository.returnBook(Long.parseLong(id));
        return "redirect:/books/"+id;
    }
}
