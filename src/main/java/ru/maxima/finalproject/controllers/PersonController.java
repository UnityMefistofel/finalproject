package ru.maxima.finalproject.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxima.finalproject.models.Person;
import ru.maxima.finalproject.repositories.PersonRepository;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // основная страница
    @GetMapping()
    public String index (Model model){
        //получение всех людей из нашей бд и пересылать их в отображение
        model.addAttribute("people", personRepository.getAllByRemovedFalse());
        return "persons/index";
    }

    @GetMapping("/{id}")
    public String elem(@PathVariable("id") String id, Model model) {
        model.addAttribute("person", personRepository.findById(Long.parseLong(id)));
        //model.addAttribute("books", bookRepository.findAllByOwnerId(Long.parseLong(id)));
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
}
