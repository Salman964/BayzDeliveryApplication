package com.bayzdelivery.service;

import com.bayzdelivery.model.Person;
import com.bayzdelivery.model.Role;
import com.bayzdelivery.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> getAll() {
        List<Person> personList = new ArrayList<>();
        personRepository.findAll().forEach(personList::add);
        return personList;
    }

    @Override
    public Person save(Person p) {
        if (p.getRole() == null) {
            throw new IllegalArgumentException("Role must be specified.");
        }

        if (p.getRole() != Role.CUSTOMER && p.getRole() != Role.DELIVERY_MAN) {
            throw new IllegalArgumentException("Invalid role.");
        }

        return personRepository.save(p);
    }

    @Override
    public Person findById(Long personId) {
        Optional<Person> dbPerson = personRepository.findById(personId);
        return dbPerson.orElse(null);
    }
}
