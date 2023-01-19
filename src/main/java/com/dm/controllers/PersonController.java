package com.dm.controllers;


import com.dm.dao.BookDAO;
import com.dm.dao.PersonDAO;
import com.dm.models.Person;
import com.dm.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/library")
public class PersonController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }


    @GetMapping
    public String startPage (){
        return "library/startPage";
    }

    @GetMapping("/people")
    public String index (Model model){
        model.addAttribute("people", personDAO.index());
        return "library/people/index";
    }

    @GetMapping("/people/{id}")
    public String showPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.showPerson(id));
        model.addAttribute("books", bookDAO.getBooksOfPerson(id));
        return "library/people/show";
    }

    @GetMapping("/people/new")
    public String newPerson(@ModelAttribute Person person){
        return "library/people/new";
    }

    @PostMapping("/people")
    public String createPerson (@ModelAttribute @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "library/people/new";
        }
        personDAO.createPerson(person);
        return "redirect:/library/people";
    }

    @GetMapping("/people/{id}/edit")
    public String editPerson (@PathVariable("id") int id, Model model){
     model.addAttribute("person", personDAO.showPerson(id));
     return "library/people/edit";
    }

    @PatchMapping("/people/{id}")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute @Valid Person person,
                               BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "library/people/edit";
        }

      personDAO.updatePerson(id,person);
      return "redirect:/library/people";
    }

    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/library/people";
    }

}
