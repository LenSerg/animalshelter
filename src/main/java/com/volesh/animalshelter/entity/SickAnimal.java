package com.volesh.animalshelter.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SickAnimal {
    private Long id;
    private Animal animal;
    private String name;
    private String description;
    private Date beginningDate;
    private Date endingDate;
    private Integer tratmentCosts;

    public SickAnimal() {
    }

    public SickAnimal(Animal animal, String name, String description, Date beginningDate, Date endingDate, Integer tratmentCosts) {
        this.animal = animal;
        this.name = name;
        this.description = description;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.tratmentCosts = tratmentCosts;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBeginningDate() {
        return beginningDate;
    }

    public String getBeginningDateString() {
        return new SimpleDateFormat("dd-MM-yyyy").format(beginningDate);
    }

    public void setBeginningDate(Date beginningDate) {
        this.beginningDate = beginningDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public String getEndingDateString() {
        if (endingDate.before(new Date(1))) {
            return "";
        } else {
        }
            return new SimpleDateFormat("dd-MM-yyyy").format(endingDate);
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public Integer getTratmentCosts() {
        return tratmentCosts;
    }
    public String getTratmentCostsString() {
        return tratmentCosts == -1 ? "" : tratmentCosts + "";
    }

    public void setTratmentCosts(Integer tratmentCosts) {
        this.tratmentCosts = tratmentCosts;
    }
}
