package com.volesh.animalshelter.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Person {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String passport;
    private String email;
    private String address;
    private String phone;
    private String role;
    private int isPrivate;
    private Date registrationDate;
    private List<AnimalStatus> statusList;

    public Person() {
    }

    public Person(String name, String surname, String patronymic, String passport, String email, String address, String phone, String role, int isPrivate, Date registrationDate) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.passport = passport;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.isPrivate = isPrivate;
        this.registrationDate = registrationDate;
    }

    public List<AnimalStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<AnimalStatus> statusList) {
        this.statusList = statusList;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getRegistrationDateString() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(registrationDate);
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
