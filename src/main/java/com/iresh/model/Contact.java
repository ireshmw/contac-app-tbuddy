package com.iresh.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity

@Table(name = "contact")
//,uniqueConstraints={@UniqueConstraint(columnNames={"phone_numb"})}
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

    @NotNull
    @Size(min = 4,max = 15)
    private String fName;

    @Size(min = 4,max = 15)
    private String lName;



    //@Pattern(regexp ="\\d{10}" ,message = "Not a valid phone number")
    @NotNull
    //@JsonIgnore
    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PhoneNumbers> phoneNumbers;
    //private String phoneNumb ;



    //@Pattern(regexp ="\\d{10}" ,message = "Not a valid phone number")
    @NotNull
    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL)
    //@JsonIgnore
    @JsonManagedReference
    private List<Emails> mails;

    private String address;

    private String comment;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Contact() {
//        List<PhoneNumbers> phoneNumbers = new ArrayList<PhoneNumbers>();
//        this.phoneNumbers= phoneNumbers;
//        List<Emails> emails = new ArrayList<Emails>();
//        this.emails = emails;

    }

    public Contact(String fName, String lName, String address, String comment) {
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.comment = comment;
    }

    public Contact(String fName, String lName, List<PhoneNumbers> phoneNumbers, List<Emails> mails, String address, String comment) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNumbers = phoneNumbers;
        this.mails = mails;
        this.address = address;
        this.comment = comment;
    }

    public Contact(String fName, String lName, String address, String comment, User user) {
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.comment = comment;
        this.user = user;
    }

    public Contact(String fName, String lName, List<PhoneNumbers> phoneNumbers, List<Emails> mails, String address, String comment, User user) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNumbers = phoneNumbers;
        this.mails = mails;
        this.address = address;
        this.comment = comment;
        this.user = user;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public List<PhoneNumbers> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumbers> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Emails> getMails() {
        return mails;
    }

    public void setMails(List<Emails> mails) {
        this.mails = mails;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", mails=" + mails +
                ", address='" + address + '\'' +
                ", comment='" + comment + '\'' +
                ", user=" + user +
                '}';
    }
}
