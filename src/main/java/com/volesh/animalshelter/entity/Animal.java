package com.volesh.animalshelter.entity;

import java.util.Date;
import java.util.List;

public class Animal {
    private Long id;
    private String name;
    private String type;
    private String breed;
    private Integer age;
    private String color;
    private Integer sex;
    private String specialSigns;
    private Integer status;
    private Date registrationDate;
    private List<SickAnimal> sickList;
    private List<Photo> photoList;
    private List<AnimalStatus> statusList;

    public Animal() {

    }

    public Animal(String name, String type, String breed, Integer age,
                  String color, Integer sex, String specialSigns, Date registrationDate,
                  Integer status) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.color = color;
        this.sex = sex;
        this.specialSigns = specialSigns;
        this.registrationDate = registrationDate;
        this.status = status;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
