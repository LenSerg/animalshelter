package com.volesh.animalshelter.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Animal {
    private Long id;
    private String name;
    private AnimalType type;
    private String breed;
    private Integer age;
    private Integer cageNumber;
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

    public Animal(String name, AnimalType type, String breed, Integer age,
                  String color, Integer cageNumber, Integer sex, String specialSigns, Date registrationDate,
                  Integer status) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.cageNumber = cageNumber;
        this.age = age;
        this.color = color;
        this.sex = sex;
        this.specialSigns = specialSigns;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public Integer getCageNumber() {
        return cageNumber;
    }

    public void setCageNumber(Integer cageNumber) {
        this.cageNumber = cageNumber;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public String getAgeString() {
        if (age == 0)
            return "";
        else
            return age+"";
    }

    public void setAge(Integer age) {
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

    public String getSexString() {
        switch (sex) {
            case 1:
                return "муж";
            case 2:
                return  "жен";
            default:
                return "неопред";
        }
    }

    public String getDateString() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(registrationDate);
    }

    public String getStatusString() {
        String statusStr = "";
        switch (Math.abs(status)) {
            case 1:
                statusStr = "В приюте";
                break;
            case 2:
                statusStr = "На передержке";
                break;
            case 3:
                statusStr = "Отдано";
                break;
            case 0:
                statusStr = "Мертво";
                break;
        }
        return statusStr;
    }
}
