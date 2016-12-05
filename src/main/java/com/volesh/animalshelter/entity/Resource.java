package com.volesh.animalshelter.entity;

public class Resource {

    private Long id;
    private String name;
    private Integer needsDegree;

    public Resource() {
    }

    public Resource(String name, Integer needsDegree) {
        this.name = name;
        this.needsDegree = needsDegree;
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

    public Integer getNeedsDegree() {
        return needsDegree;
    }

    public void setNeedsDegree(Integer needsDegree) {
        this.needsDegree = needsDegree;
    }

    @Override
    public String toString() {
        return getName();
    }
}


