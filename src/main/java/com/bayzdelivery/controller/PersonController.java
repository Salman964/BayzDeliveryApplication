package com.bayzdelivery.controller;

import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping
  public ResponseEntity<Person> register(@RequestBody Person p) {
    if (p.getRole() == null) {
        throw new IllegalArgumentException("Role must not be null. Please provide either CUSTOMER or DELIVERY_MAN.");
    }
    return ResponseEntity.ok(personService.save(p));
  }

  @GetMapping
  public ResponseEntity<List<Person>> getAllPersons() {
      return ResponseEntity.ok(personService.getAll());
  }

  @GetMapping("/{personId}")
  public ResponseEntity<Person> getPersonById(@PathVariable Long personId) {
      Person person = personService.findById(personId);
      if (person != null) {
          return ResponseEntity.ok(person);
      }
      return ResponseEntity.notFound().build();
  }
}
