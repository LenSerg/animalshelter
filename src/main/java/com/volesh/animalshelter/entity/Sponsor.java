package com.volesh.animalshelter.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Sponsor {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Integer type;
    private String contactPerson;
    private Date registrationDate;
    private List<Sponsorship> sponsorshipList;

    public Sponsor() {
    }

    public Sponsor(String name, String address, String phone, String email, Integer type, String contactPerson, Date registrationDate) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.type = type;
        this.contactPerson = contactPerson;
        this.registrationDate = registrationDate;
    }

    public List<Sponsorship> getSponsorshipList() {
        return sponsorshipList;
    }

    public void setSponsorshipList(List<Sponsorship> sponsorshipList) {
        this.sponsorshipList = sponsorshipList;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
