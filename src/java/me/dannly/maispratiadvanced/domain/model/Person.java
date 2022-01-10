/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.dannly.maispratiadvanced.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author vinix
 */
public class Person {

    private final Integer id;
    private String name;
    private final Long createdAt;
    private Long birth;
    private Long lastModified;
    private Long phone;
    private Short age;

    public Person(Integer id, Long createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Person(Integer id, String name, Long createdAt, Long birth, Long lastModified, Long phone, Short age) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.birth = birth;
        this.lastModified = lastModified;
        this.phone = phone;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBirth() {
        return birth;
    }

    public void setBirth(Long birth) {
        this.birth = birth;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public Student toStudent(double score) {
        return new Student(id, name, createdAt, birth, lastModified, phone, age, score);
    }

    public String getFormattedPhone() {
        final String toString = phone.toString();
        return "+%s (%s) %s-%s".formatted(
                toString.substring(0, 2),
                toString.substring(2, 4),
                toString.substring(4, 8),
                toString.substring(8, 12)
        );
    }
}
