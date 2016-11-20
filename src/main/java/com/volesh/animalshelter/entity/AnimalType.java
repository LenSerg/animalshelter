package com.volesh.animalshelter.entity;

public class AnimalType {
    private Long id;
    private String name;

    public AnimalType() {
    }

    public AnimalType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}


