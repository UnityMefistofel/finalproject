package ru.maxima.finalproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Tuple;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class BookExt extends Book {

    private Long entity;
    private Long linked;
    private int serialnum;
    private LocalDateTime gaveout;
    private LocalDateTime returned;

    public BookExt() {}

    public BookExt(Long id, Long entity, Long linked, String name,
                   String author, int yearOfProduction, String annotation,
                   int serialnum, LocalDateTime gaveout) {
        this.setId(id);
        this.setEntity(entity);
        this.setLinked(linked);
        this.setName(name);
        this.setAuthor(author);
        this.setYearOfProduction(yearOfProduction);
        this.setAnnotation(annotation);
        this.setSerialnum(serialnum);
        this.setGaveout(gaveout);
    }

    public BookExt(Tuple tpl) {
        this.setId(tpl.get("id", Long.class));
        this.setEntity(tpl.get("entity", Long.class));
        this.setLinked(tpl.get("linked", Long.class));
        this.setName(tpl.get("name", String.class));
        this.setAuthor(tpl.get("author", String.class));
        this.setYearOfProduction(tpl.get("year_of_production", Integer.class));
        this.setAnnotation(tpl.get("annotation", String.class));
        this.setSerialnum(tpl.get("serialnum", Integer.class));
        this.setGaveout(tpl.get("gaveout", Timestamp.class).toLocalDateTime());
    }

    public Long getEntity() {
        return entity;
    }

    public void setEntity(Long entity) {
        this.entity = entity;
    }

    public Long getLinked() {
        return linked;
    }

    public void setLinked(Long linked) {
        this.linked = linked;
    }

    public int getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(int serialnum) {
        this.serialnum = serialnum;
    }

    public LocalDateTime getGaveout() {
        return gaveout;
    }

    public void setGaveout(LocalDateTime gaveout) {
        this.gaveout = gaveout;
    }

    public LocalDateTime getReturned() {
        return returned;
    }

    public void setReturned(LocalDateTime returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return this.getName() + " (" + this.getSerialnum() + ")";
    }
}
