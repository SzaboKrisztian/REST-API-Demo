package com.krisztianszabo.restdemo;

import com.krisztianszabo.restdemo.model.Person;
import com.krisztianszabo.restdemo.model.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ApiController {

  private final PersonRepository persons;

  public ApiController(PersonRepository personRepository) {
    this.persons = personRepository;
  }

  @GetMapping("/api/person/")
  @ResponseBody
  public List<Person> getAllPeeps() {
    return persons.findAll();
  }

  @GetMapping("/api/person/{id}")
  @ResponseBody
  public Person getPerson(@PathVariable("id") Integer id) {
    Optional<Person> result = persons.findById(id);
    return result.isPresent() ? result.get() : null;
  }

  @PostMapping("/api/person/")
  @ResponseBody
  public String createPerson(@RequestBody Person person) {
    System.out.println(person);
    persons.save(person);
    return "Success";
  }

  @PostMapping("/api/person/{id}")
  @ResponseBody
  public String updatePerson(@PathVariable("id") Integer id,
                             @RequestBody Person person) {
    Optional<Person> result = persons.findById(id);
    if (result.isPresent()) {
      Person currentPerson = result.get();
      currentPerson.setFirstName(person.getFirstName());
      currentPerson.setLastName(person.getLastName());
      currentPerson.setEmail(person.getEmail());
      currentPerson.setGender(person.getGender());
      currentPerson.setBirthday(person.getBirthday());
      if (persons.save(currentPerson) == null) {
        return "Entry successfully updated.";
      } else {
        return "Error updating entry";
      }
    } else {
      return "No such id.";
    }
  }

  @DeleteMapping("/api/person/{id}")
  public String deletePerson(@PathVariable("id") Integer id) {
    if (persons.findById(id).isPresent()) {
      persons.deleteById(id);
      return "Entry deleted successfully.";
    } else {
      return "No such entry.";
    }
  }
}
