package com.example.broadcastreceiverapp;

public class CustomerModel {

    private int id;
    private String customerEmail;
    private String customerPassword;
    private String customerAge;
    private String customerGender;

    //constructors

    public CustomerModel(int id, String customerEmail, String customerPassword, String customerAge, String customerGender) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerAge = customerAge;
        this.customerGender = customerGender;
    }

    public CustomerModel() {
    }

    //toString is necessary for printing the contents of a class object

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerPassword='" + customerPassword + '\'' +
                ", customerAge=" + customerAge +
                ", customerGender='" + customerGender + '\'' +
                '}';
    }

    //getters sand setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(String customerAge) {
        this.customerAge = customerAge;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }
}
