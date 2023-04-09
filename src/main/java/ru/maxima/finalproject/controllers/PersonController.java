package ru.maxima.finalproject.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxima.finalproject.models.BookExt;
import ru.maxima.finalproject.models.Person;
import ru.maxima.finalproject.repositories.BookRepository;
import ru.maxima.finalproject.repositories.PersonRepository;

import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {

    public class PersonExt extends Person {
        private int booksOnHand;
        private List<BookExt> lstBooks;

        public PersonExt() {
            this.setId(Integer.toUnsignedLong(0));
        }

        public PersonExt(Person pers) {
            this.setId(pers.getId());
            this.setUsername(pers.getUsername());
            this.setAge(pers.getAge());
            this.setPhoneNumber(pers.getPhoneNumber());
            this.setEmail(pers.getEmail());
            this.setRemoved(pers.getRemoved());
        }

        public int getBooksOnHand() {
            return booksOnHand;
        }

        public void setBooksOnHand(int booksCnt) {
            this.booksOnHand = booksCnt;
        }

        public List<BookExt> getLstBooks() {
            return lstBooks;
        }

        public void setLstBooks(List<BookExt> lstBooks) {
            this.lstBooks = lstBooks;
        }
    }

    private final PersonRepository personRepository;

    private final BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PersonController(PersonRepository personRepository,
                            BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    // основная страница
    @GetMapping()
    public String index (Model model){
        model.addAttribute("people",
                personRepository.getAllByRemovedFalse()
                        .stream()
                        .map(
                            person -> {
                                PersonExt personExt = new PersonExt(person);
                                personExt.setBooksOnHand(personRepository.getBooksCntByOwner(person.getId()));
                                return personExt;
                            })
                        .toList());
        return "persons/index";
    }

    @GetMapping("/{id}")
    public String elem(@PathVariable("id") String id, Model model) {
        Person person = personRepository.findById(Long.parseLong(id)).orElse(new Person());
        PersonExt personExt = new PersonExt(person);
        List<Tuple> lstTpl = entityManager.createNativeQuery(
                "select b.id,be.id as entity,bh.id as linked," +
                        "b.name,b.author,b.year_of_production,b.annotation,be.serialnum,bh.gaveout " +
                        "from books b " +
                        "   join booksentity be on b.id = be.book_id " +
                        "       join booksonhands bh on be.id = bh.bookentity_id " +
                        "where b.removed = false " +
                        "  and be.removed = false " +
                        "  and bh.returned is null " +
                        "  and bh.person_id = :persId", Tuple.class)
                    .setParameter("persId",Long.parseLong(id))
                    .getResultList();
        List<BookExt> lstBooks = lstTpl.stream().map(tpl -> new BookExt(tpl)).toList();
        personExt.setLstBooks(lstBooks);
        personExt.setBooksOnHand(personRepository.getBooksCntByOwner(personExt.getId()));
        model.addAttribute("person", personExt);
        return "persons/elem";
    }

    @GetMapping("/create")
    public String addPerson(Model model) {
        model.addAttribute("person", new Person());
        return "persons/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        personRepository.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") String id) {
        model.addAttribute("person", personRepository.findById(Long.parseLong(id)));
        return "persons/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("person") Person person) {
        personRepository.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        //accRepository.deletePerson(Long.parseLong(id));
        int updateCount = entityManager.createNativeQuery("update profile set isDel = :isdel where id = :id",
                    Person.class)
            .setParameter("isdel", true)
            .setParameter("id", Long.parseLong(id))
            .executeUpdate();
        
        return "redirect:/persons";
    }

    public int getBooksCnt(Long ownerId) {
        Person pers = personRepository.findById(ownerId).orElse(null);
        int sel = personRepository.getBooksCntByOwner(ownerId);

        return sel;
    }
}
