package com.dm.controllers;


import com.dm.dao.BookDAO;
import com.dm.dao.PersonDAO;
import com.dm.models.Book;
import com.dm.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/library")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;


    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }


    @GetMapping("/books")
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "library/books/index";
    }


    @GetMapping("/books/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("owner", bookDAO.getOwner(id));
        model.addAttribute("person", new Person());
        model.addAttribute("book", bookDAO.showBook(id));
        model.addAttribute("people", personDAO.index());
        if (bookDAO.getOwner(id) != null){
            model.addAttribute("condition", true );
        }
        else{
            model.addAttribute("condition", false );
        }
        return "library/books/show";
    }

    @GetMapping("/books/new")
    public String newPerson(@ModelAttribute Book book) {
        return "library/books/new";
    }

    @PostMapping("/books")
    public String createBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "library/books/new";
        }
        bookDAO.createBook(book);
        return "redirect:/library/books";
    }

    @GetMapping("/books/{id}/edit")
    public String editBook (@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.showBook(id));
        return "library/books/edit";
    }

    @PatchMapping("/books/{id}")
    public String updateBooks (@ModelAttribute @Valid Book book, BindingResult bindingResult,
                               @PathVariable("id") int id) {

        if (bindingResult.hasErrors()){
            return "library/books/edit";
        }
        bookDAO.updateBook(id, book);
        return "redirect:/library/books";
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook (@PathVariable("id") int id){
        bookDAO.deleteBook(id);
        return "redirect:/library/books";
    }

    @PatchMapping("/books/{id}/back")
    public String backBook(@PathVariable int id){
        bookDAO.backBook(id);
        return "redirect:/library/books/"+id;
    }

    @PatchMapping("/books/{id}/set")
    public String setBook(@PathVariable int id, @ModelAttribute Person person){
        System.out.println(1);
        bookDAO.setBook(id,person.getId());
        System.out.println(2);
        return "redirect:/library/books/"+id;
    }

}
