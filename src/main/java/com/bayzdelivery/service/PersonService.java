package com.bayzdelivery.service;

import com.bayzdelivery.model.Person;
import java.util.List;

public interface PersonService {

  List<Person> getAll();

  Person save(Person p);

  Person findById(Long personId);
}
