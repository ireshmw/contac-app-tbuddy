package com.iresh.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by iresh on 12/3/2016.
 */
@Entity
public class PhoneNumbers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;

    @NotNull
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    @JsonBackReference
    private Contact contact;

    public PhoneNumbers() {
    }

    public PhoneNumbers(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumbers(String phoneNumber, Contact contact) {
        this.phoneNumber = phoneNumber;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
