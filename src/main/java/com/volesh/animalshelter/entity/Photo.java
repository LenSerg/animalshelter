package com.volesh.animalshelter.entity;

import java.util.Date;

public class Photo {
    private Long id;
    private Animal animal;
    private String url;
    private Date addingDate;

    public Photo() {

    }

    public Photo(Animal animal, String url, Date addingDate) {
        this.animal = animal;
        this.url = url;
        this.addingDate = addingDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }
}
