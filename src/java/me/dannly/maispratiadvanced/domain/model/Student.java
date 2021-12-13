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
public class Student extends Person {
    
    private Double score;
    
    public Student(int id, LocalDateTime createdAt) {
        super(id, createdAt);
    }
    
    public Student(Integer id, String name, LocalDateTime createdAt, LocalDate birth, LocalDateTime lastModified, Long phone, Short age) {
        super(id, name, createdAt, birth, lastModified, phone, age);
    }
    
    public Student(Integer id, String name, LocalDateTime createdAt, LocalDate birth, LocalDateTime lastModified, Long phone, Short age, Double score) {
        super(id, name, createdAt, birth, lastModified, phone, age);
        this.score = score;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
