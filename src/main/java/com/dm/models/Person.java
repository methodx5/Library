package com.dm.models;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {

    private int id;
    @NotEmpty(message = "Not Empty")
    @Size(min = 2, max = 30, message = "Size between 2 and 30")
    private String fullName;
    @NotEmpty(message = "Not Empty")
    @Min(value = 1900, message = "Year must be bigger than 1900")
    private String birthdayDate;


    public Person(){

    }
    public Person(int id, String fullName, String birthdayDate) {
        this.id = id;
        this.fullName = fullName;
        this.birthdayDate = birthdayDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }
}
