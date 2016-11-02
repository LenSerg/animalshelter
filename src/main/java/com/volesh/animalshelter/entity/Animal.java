package com.volesh.animalshelter.entity;

import java.util.Date;
import java.util.List;

public class Animal {
    private Long id;
    private String name;
    private String type;
    private String breed;
    private int age;
    private String color;
    private int sex;
    private String specialSigns;
    private String status;
    private Date registrationDate;
    private List<SickAnimal> sickList;
    private List<Photo> photoList;
    private List<AnimalStatus> statusList;

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public List<AnimalStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<AnimalStatus> statusList) {
        this.statusList = statusList;
    }

    public List<SickAnimal> getSickList() {
        return sickList;
    }

    public void setSickList(List<SickAnimal> sickList) {
        this.sickList = sickList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSpecialSigns() {
        return specialSigns;
    }

    public void setSpecialSigns(String specialSigns) {
        this.specialSigns = specialSigns;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
