package com.iresh.model;

import java.util.ArrayList;

/**
 * Created by iresh on 11/19/2016.
 */
public class RespondState {
    private int state;
    private ArrayList<ErrorObjects> errorObjects;
    private Contact contact;

    public RespondState(){
        ArrayList<ErrorObjects> er = new ArrayList<>();
        this.errorObjects=er;

    }

//    public RespondState(int state, Contact contact) {
//        this.state = state;
//        this.contact = contact;
//    }

    public RespondState(int state, ArrayList<ErrorObjects> errorObjects) {
        this.state = state;
        this.errorObjects = errorObjects;
    }

    public RespondState(int state, ArrayList<ErrorObjects> errorObjects, Contact contact) {
        this.state = state;
        this.errorObjects = errorObjects;
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<ErrorObjects> getErrorObjects() {
        return errorObjects;
    }

    public void setErrorObjects(ErrorObjects errorObjects) {
        this.errorObjects.add(errorObjects);
    }

    @Override
    public String toString() {
        return "RespondState{" +
                "state=" + state +
                ", errorObjects=" + errorObjects +
                '}';
    }
}
