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
    private final LocalDateTime createdAt;
    private LocalDate birth;
    private LocalDateTime lastModified;
    private Long phone;
    private Short age;

    public Person(Integer id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Person(Integer id, String name, LocalDateTime createdAt, LocalDate birth, LocalDateTime lastModified, Long phone, Short age) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
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
