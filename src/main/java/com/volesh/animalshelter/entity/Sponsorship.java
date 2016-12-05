package com.volesh.animalshelter.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sponsorship {

    private Long id;
    private Resource resource;
    private Sponsor sponsor;
    private String amount;
    private Date addingDate;

    public Sponsorship() {
    }

    public Sponsorship(Resource resource, Sponsor sponsor, String amount, Date addingDate) {
        this.resource = resource;
        this.sponsor = sponsor;
        this.amount = amount;
        this.addingDate = addingDate;
    }

    public Resource getResource() {
        return resource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getAddingDate() {
        return addingDate;
    }

    public String getAddingDateString() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(addingDate);
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }
}
