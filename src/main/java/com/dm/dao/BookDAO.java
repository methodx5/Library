package com.dm.dao;


import com.dm.models.Book;
import com.dm.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?",
                new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().orElse(null);
    }

    public void createBook(Book book){
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?,?,?)", book.getName(),
                book.getAuthor(), book.getYear());
    }

    public void updateBook (int id, Book book){
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void deleteBook (int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public List<Book> getBooksOfPerson(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?",
                new BeanPropertyRowMapper<>(Book.class), id);
    }

    public Person getOwner(int book_id){
        int person_id = 0;
        person_id = jdbcTemplate.query("SELECT (person_id) FROM Book WHERE id=?", new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                return rs.getInt(1);
            }
        },book_id);
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new BeanPropertyRowMapper<>(Person.class),
                person_id).stream().findAny().orElse(null);
    }

    public void backBook (int book_id){
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE id=?", book_id);
    }

    public void setBook(int id, int p){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?",p,id);
    }

}
