package com.volesh.animalshelter.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AnimalStatus {
    private Long id;
    private Animal animal;
    private Person person;
    private String statusString;
    private Date statusDate;


    public AnimalStatus() {
    }

    public AnimalStatus(Animal animal, Person person, String statusString, Date statusDate) {
        this.animal = animal;
        this.person = person;
        this.statusString = statusString;
        this.statusDate = statusDate;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public String getStatusDateString() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(statusDate);
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
}
