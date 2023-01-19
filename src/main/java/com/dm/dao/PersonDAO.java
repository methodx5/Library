package com.dm.dao;


import com.dm.models.Book;
import com.dm.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        List<Person> list = jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));


     return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));

    }

    public Person showPerson(int id){
       return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
               new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }

    public Person showPerson(String name){
        return jdbcTemplate.query("SELECT * FROM Person WHERE fullName=?",
                new BeanPropertyRowMapper<>(Person.class), name).stream().findAny().orElse(null);
    }

    public void createPerson(Person person){
       jdbcTemplate.update("INSERT INTO Person(fullName, birthdayDate) VALUES (?,?)", person.getFullName(),
               person.getBirthdayDate());
    }

    public void updatePerson (int id, Person person){
       jdbcTemplate.update("UPDATE Person SET fullName=?, birthdayDate=? WHERE id=?",
               person.getFullName(), person.getBirthdayDate(), id);
    }

    public void deletePerson (int id){
       jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }


}
