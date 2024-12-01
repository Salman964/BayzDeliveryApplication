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

  @GetMapping
  public ResponseEntity<List<Person>> getAllPersons() {
      return ResponseEntity.ok(personService.getAll());
  }

}
