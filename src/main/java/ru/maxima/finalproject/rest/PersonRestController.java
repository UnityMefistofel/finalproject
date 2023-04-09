package ru.maxima.finalproject.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.maxima.finalproject.models.Person;
import ru.maxima.finalproject.repositories.PersonRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonRestController {

    private PersonRepository personRepository;

    @Autowired
    PersonRestController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/{personId}")
    public Optional<Person> readUserProfile(@PathVariable("personId") Long id) {
        return this.personRepository.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody Person person) {
        Person result = personRepository.save(person);

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @PostMapping("/{personId}/edit")
    public ResponseEntity<?> edit(@PathVariable("personId") Long id,
                           @RequestBody Person curruser) {
        this.validateUser(id);
        return this.personRepository
                .findById(id)
                .map(acc -> {
                    Person result = personRepository.save(curruser);

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri());

                    return new ResponseEntity<>(null,httpHeaders,HttpStatus.OK);
                }).get();
    }

    private void validateUser(Long personId) {
        this.personRepository.findById(personId).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }
}
