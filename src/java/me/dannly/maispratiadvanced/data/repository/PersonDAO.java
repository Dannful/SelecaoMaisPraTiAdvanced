/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.dannly.maispratiadvanced.data.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.dannly.maispratiadvanced.domain.model.Person;
import me.dannly.maispratiadvanced.domain.model.Student;
import me.dannly.maispratiadvanced.domain.repository.DatabaseRepository;

/**
 *
 * @author vinix
 */
public class PersonDAO extends DatabaseRepository<Person> {

    public PersonDAO() {
        super("CREATE TABLE IF NOT EXISTS `mydb`.`people` (\n"
                + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                + "  `name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,\n"
                + "  `phone` BIGINT(14) UNSIGNED NOT NULL,\n"
                + "  `birth` BIGINT NOT NULL,\n"
                + "  `last_modified` BIGINT NOT NULL,\n"
                + "  `age` TINYINT(3) UNSIGNED NOT NULL,\n"
                + "  `score` DECIMAL UNSIGNED NULL,\n"
                + "  `created_at` DATETIME NOT NULL,\n"
                + "  PRIMARY KEY (`id`),\n"
                + "  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE,\n"
                + "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)\n"
                + "ENGINE = InnoDB", "INSERT INTO people (id, name, phone, birth, last_modified, age, score, created_at)"
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
            value.getBirth(),
            value.getLastModified(),
            value.getAge(), score, value.getCreatedAt()};
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
            final long createdAt = resultSet.getLong("created_at");
            final long birth = resultSet.getLong("birth");
            final long lastModified = resultSet.getLong("last_modified");
            final long phone = resultSet.getLong("phone");
            final short age = resultSet.getShort("age");
            return score == null ? new Person(id, name, createdAt, birth, lastModified, phone, age) : new Student(id, name, createdAt, birth, lastModified, phone, age, score);
        } catch (SQLException e) {
            return null;
        }
    }
}
