/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.dannly.maispratiadvanced.data.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import me.dannly.maispratiadvanced.domain.model.Person;
import me.dannly.maispratiadvanced.domain.model.Student;
import me.dannly.maispratiadvanced.domain.repository.DatabaseRepository;

/**
 *
 * @author vinix
 */
public class PersonDAO extends DatabaseRepository<Person> {

    public PersonDAO() {
        super("INSERT INTO people (id, name, phone, birth, last_modified, age, score, created_at)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?, "
                + "phone=?, birth=?, last_modified=?, age=?, score=?, created_at=?",
                "DELETE FROM people WHERE id = ?", "SELECT * FROM people");
    }

    @Override
    public Object[] setupInsert(Person value) {
        final Double score = value instanceof Student ? ((Student) value).getScore() : null;
        return new Object[]{
            value.getId(),
            value.getName(), value.getPhone(),
            value.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            value.getLastModified().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
            value.getAge(), score, value.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))};
    }

    @Override
    public Object setupDelete(Person value) {
        return value.getId();
    }

    @Override
    public Person setupRead(ResultSet resultSet) {
        try {
            Double score = resultSet.getObject("score", Double.class);
            final int id = resultSet.getInt("id");
            final String name = resultSet.getString("name");
            final LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            final LocalDate birth = resultSet.getDate("birth").toLocalDate();
            final LocalDateTime lastModified = resultSet.getTimestamp("last_modified").toLocalDateTime();
            final long phone = resultSet.getLong("phone");
            final short age = resultSet.getShort("age");
            return score == null ? new Person(id, name, createdAt, birth, lastModified, phone, age) : new Student(id, name, createdAt, birth, lastModified, phone, age, score);
        } catch (SQLException e) {
            return null;
        }
    }
}
