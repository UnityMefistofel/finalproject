package ru.maxima.finalproject.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int yearOfProduction;

    private String author;

    private String annotation;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private LocalDateTime removedAt;

    private String createdPerson;

    private String updatedPerson;

    private String removedPerson;

    private boolean removed;

    public Book() {
        this.id = Integer.toUnsignedLong(0);
    }

    public Book(String name, int yearOfProduction, String author, String annotation) {
        this.name = name;
        this.yearOfProduction = yearOfProduction;
        this.author = author;
        this.annotation = annotation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getRemovedAt() {
        return removedAt;
    }

    public void setRemovedAt(LocalDateTime removedAt) {
        this.removedAt = removedAt;
    }

    public String getCreatedPerson() {
        return createdPerson;
    }

    public void setCreatedPerson(String createdPerson) {
        this.createdPerson = createdPerson;
    }

    public String getUpdatedPerson() {
        return updatedPerson;
    }

    public void setUpdatedPerson(String updatedPerson) {
        this.updatedPerson = updatedPerson;
    }

    public String getRemovedPerson() {
        return removedPerson;
    }

    public void setRemovedPerson(String removedPerson) {
        this.removedPerson = removedPerson;
    }

    public boolean getRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

}
